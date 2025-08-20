package vn.flast.utils;
/**************************************************************************/
/*  app.java                                                              */
/**************************************************************************/
/*                       Tệp này là một phần của:                         */
/*                             Open CDP                                   */
/*                        https://flast.vn                                */
/**************************************************************************/
/* Bản quyền (c) 2025 - này thuộc về các cộng tác viên Flast Solution     */
/* (xem AUTHORS.md).                                                      */
/* Bản quyền (c) 2024-2025 Long Huu, Thành Trung                          */
/*                                                                        */
/* Bạn được quyền sử dụng phần mềm này miễn phí cho bất kỳ mục đích nào,  */
/* bao gồm sao chép, sửa đổi, phân phối, bán lại…                         */
/*                                                                        */
/* Chỉ cần giữ nguyên thông tin bản quyền và nội dung giấy phép này trong */
/* các bản sao.                                                           */
/*                                                                        */
/* Đội ngũ phát triển mong rằng phần mềm được sử dụng đúng mục đích và    */
/* có trách nghiệm                                                        */
/**************************************************************************/

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.hibernate.query.spi.QueryParameterImplementor;
import org.hibernate.query.sqm.internal.QuerySqmImpl;
import org.hibernate.query.sqm.sql.internal.StandardSqmTranslator;
import org.hibernate.query.sqm.tree.expression.SqmParameter;
import org.hibernate.query.sqm.tree.expression.ValueBindJpaCriteriaParameter;
import org.hibernate.query.sqm.tree.select.SqmSelectStatement;
import org.hibernate.sql.ast.tree.select.SelectStatement;
import vn.flast.pagination.Ipage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@SuppressWarnings({"unchecked", "UnusedReturnValue"})
@Slf4j
public class EntityQuery<E> {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
    private final CriteriaQuery<E> criteriaQuery;
    private final Root<E> root;
    private final List<Predicate> predicates = new ArrayList<>();
    private Integer firstResult;
    private Integer maxResults;
    private final List<Order> orders = new ArrayList<>();

    private EntityQuery(EntityManager entityManager, Class<E> entityClass) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.criteriaQuery = criteriaBuilder.createQuery(entityClass);
        this.root = criteriaQuery.from(criteriaQuery.getResultType());
    }

    public static <T> EntityQuery<T> create(EntityManager entityManager, Class<T> entityClass) {
        return new EntityQuery<>(entityManager, entityClass);
    }

    public List<E> list() {
        TypedQuery<E> typedQuery = prepareSelectTypedQuery();
        typedQuery.setFirstResult(firstResult != null ? firstResult : 0);
        typedQuery.setMaxResults(maxResults != null ? maxResults : 200);
        return typedQuery.getResultList();
    }

	public E uniqueResult() {
    	E result = null;
    	try {
    		TypedQuery<E> typedQuery = prepareSelectTypedQuery();
    		typedQuery.setMaxResults(1);
    	    result = typedQuery.getSingleResult();
    	} catch (NoResultException ignored) {

        }
        return result;
    }

    public long count() {
        QuerySqmImpl<?> q = entityManager.createQuery(criteriaQuery).unwrap(QuerySqmImpl.class);
        StandardSqmTranslator<?> converter = new StandardSqmTranslator<>(
            q.getSqmStatement(),
            q.getQueryOptions(),
            q.getDomainParameterXref(),
            q.getParameterBindings(),
            q.getLoadQueryInfluencers(),
            q.getSessionFactory(),
            true
        );
        try {
            SelectStatement sqlAst = converter.visitSelectStatement((SqmSelectStatement<?>) q.getSqmStatement());
            String generatedSql = q.getSessionFactory()
                .getJdbcServices()
                .getDialect()
                .getSqlAstTranslatorFactory()
                .buildSelectTranslator(q.getSessionFactory(), sqlAst)
                .translate(null, q.getQueryOptions())
                .getSqlString();

            String finalCount = generatedSql.replaceAll("(select)[^&]*(from)", "$1 COUNT(*) $2");
            Query countQuery = entityManager.createNativeQuery(finalCount);
            int i = 1;
            for (Map.Entry<QueryParameterImplementor<?>, List<SqmParameter<?>>> param : q.getDomainParameterXref().getQueryParameters().entrySet()) {
                ValueBindJpaCriteriaParameter<?> value = (ValueBindJpaCriteriaParameter<?>)param.getKey();
                countQuery.setParameter(i, value.getValue() instanceof Enum ? ((Enum<?>) value.getValue()).name() : value.getValue());
                i++;
            }
            return (long) countQuery.getSingleResult();
        } catch (Exception e) {
            log.error("===== Lỗi count entityQuery: {} ======", e.getMessage());
            return 0L;
        }
    }

    private TypedQuery<E> prepareSelectTypedQuery() {
        criteriaQuery.select(root);
        if(!predicates.isEmpty()) {
            criteriaQuery.where(predicates.toArray(new Predicate[0]));
        }
        criteriaQuery.orderBy(orders);
        return entityManager.createQuery(criteriaQuery);
    }

    public EntityQuery<E> leftJoinFetch(String attribute) {
        root.fetch(attribute, JoinType.LEFT);
        return this;
    }

    public EntityQuery<E> addAscendingOrderBy(String path) {
        orders.add(criteriaBuilder.asc(toJpaPath(path)));
        return this;
    }

    public EntityQuery<E> addDescendingOrderBy(String path) {
        orders.add(criteriaBuilder.desc(toJpaPath(path)));
        return this;
    }

    public EntityQuery<E> setFirstResult(Integer firstResult) {
        this.firstResult = firstResult;
        return this;
    }

    public EntityQuery<E> setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
        return this;
    }

    public EntityQuery<E> objectEqualsTo(String path, Object value) {
        if (value != null) {
            addEqualPredicate(path, value);
        }
        return this;
    }

    public Optional<Predicate> objectEqualsToPredicate(String path, Object value) {
        if (value != null) {
            return Optional.of(equalPredicate(path, value));
        }
        return Optional.empty();
    }

    public EntityQuery<E> like(String path, String value) {
        if (StringUtils.isNotBlank(value)) {
            predicates.add(criteriaBuilder.like(toJpaPath(path), '%' + value + '%'));
        }
        return this;
    }

    public EntityQuery<E> dateBetween(String path, Date fromDate, Date toDate) {
        if (fromDate != null && toDate != null) {
            predicates.add(criteriaBuilder.between(toJpaPath(path), fromDate, toDate));
        } else if (fromDate != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(toJpaPath(path), fromDate));
        } else if (toDate != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(toJpaPath(path), toDate));
        }
        return this;
    }

    /**
     * Thực hiện LEFT JOIN với các bảng liên kết
     * Ví dụ:
     * - Join 2 bảng: .leftJoin("department")
     *                .objectEqualsTo("department.name", "ABC Corp")
     * - Join 3 bảng: .leftJoin("department.company")
     *                .objectEqualsTo("department.company.name", "ABC Corp")
     * @param path Đường dẫn join (ví dụ: "department", "department.company")
     * @return this
     */
    public EntityQuery<E> leftJoin(String path) {
        String[] parts = path.split("\\.");
        From<?, ?> from = root;
        for (String part : parts) {
            from = from.join(part, JoinType.LEFT);
        }
        return this;
    }

    public EntityQuery<E> innerJoin(String path) {
        String[] parts = path.split("\\.");
        From<?, ?> from = root;
        for (String part : parts) {
            from = from.join(part, JoinType.INNER);
        }
        return this;
    }

	@SafeVarargs
    public final EntityQuery<E> addInDisjunction(Optional<Predicate>... optionalPredicates) {
        List<Predicate> predicateList = Arrays.stream(optionalPredicates).filter(Optional::isPresent).map(Optional::get).toList();
        if (predicateList.size() > 1) {
            predicates.add(criteriaBuilder.or(predicateList.toArray(new Predicate[0])));
        } else if (predicateList.size() == 1) {
            predicates.add(predicateList.get(0));
        }
        return this;
    }

    public EntityQuery<E> stringEqualsTo(String path, String value) {
        if (StringUtils.isNotBlank(value)) {
            addEqualPredicate(path, value);
        }
        return this;
    }
    
    public EntityQuery<E> booleanEqualsTo(String path, Boolean value){
    	if(value != null) {
    		addEqualPredicate(path, value);
    	}
    	return this;
    }
    
    public EntityQuery<E> enumEqualsTo(String path, Enum<?> value){
    	if(value != null) {
    		addEqualPredicate(path, value);
    	}
    	return this;
    }

    @SuppressWarnings({ "rawtypes" })
	public EntityQuery<E> greaterThanOrEqualsTo(String path, Comparable comparable) {
        if (Objects.nonNull(comparable)) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(toJpaPath(path), comparable));
        }
        return this;
    }
    
    @SuppressWarnings({ "rawtypes" })
	public EntityQuery<E> greaterThan(String path, Comparable comparable) {
        if (Objects.nonNull(comparable)) {
            predicates.add(criteriaBuilder.greaterThan(toJpaPath(path), comparable));
        }
        return this;
    }

    @SuppressWarnings({ "rawtypes" })
    public EntityQuery<E> lessThanOrEqualsTo(String path, Comparable comparable) {
        if (Objects.nonNull(comparable)) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(toJpaPath(path), comparable));
        }
        return this;
    }

    public EntityQuery<E> between(String path, Date firstDate, Date secondDate) {
        if (Objects.nonNull(firstDate) && Objects.nonNull(secondDate)) {
            predicates.add(criteriaBuilder.between(toJpaPath(path), firstDate, secondDate));
        }
        return this;
    }

    public EntityQuery<E> in(String path, Collection<?> collection) {
        if (Objects.nonNull(collection) && !collection.isEmpty()) {
            predicates.add(criteriaBuilder.in(toJpaPath(path)).value(collection));
        }
        return this;
    }

    @SuppressWarnings({ "rawtypes" })
    public EntityQuery<E> notIn(String path, Collection collection) {
        if (!collection.isEmpty()) {
            predicates.add(criteriaBuilder.in(toJpaPath(path)).value(collection).not());
        }
        return this;
    }

    public EntityQuery<E> fieldLessThanOrEqualTo(String left, String right) {
        Path<BigDecimal> leftPath = toJpaPath(left);
        Path<BigDecimal> rightPath = toJpaPath(right);
        predicates.add(criteriaBuilder.lessThanOrEqualTo(leftPath, rightPath));
        return this;
    }

    public EntityQuery<E> fieldLessThan(String left, String right) {
        Path<BigDecimal> leftPath = toJpaPath(left);
        Path<BigDecimal> rightPath = toJpaPath(right);
        predicates.add(criteriaBuilder.lessThan(leftPath, rightPath));
        return this;
    }

    public EntityQuery<E> integerNotEquals(String path, Integer value) {
        if (value != null) {
            predicates.add(criteriaBuilder.notEqual(toJpaPath(path), value));
        }
        return this;
    }

    public EntityQuery<E> stringNotEquals(String path, String value) {
        if (StringUtils.isNotEmpty(value)) {
            predicates.add(criteriaBuilder.notEqual(toJpaPath(path), value));
        }
        return this;
    }
    
    public EntityQuery<E> integerEqualsTo(String path, Integer value) {
    	if(value != null) {
    		addEqualPredicate(path, value);
    	}
        return this;
    }

    public EntityQuery<E> longEqualsTo(String path, Long value) {
        if(value != null) {
            addEqualPredicate(path, value);
        }
        return this;
    }

    public EntityQuery<E> dateIsNotNull(String path) {
        predicates.add(criteriaBuilder.isNotNull(toJpaPath(path)));
        return this;
    }

    public EntityQuery<E> dateIsNull(String path) {
        predicates.add(criteriaBuilder.isNull(toJpaPath(path)));
        return this;
    }

    private void addEqualPredicate(String path, Object value) {
        predicates.add(equalPredicate(path, value));
    }

    private Predicate equalPredicate(String path, Object value) {
        return criteriaBuilder.equal(toJpaPath(path), value);
    }

    public void orPredicate(Predicate ...predicate) {
        predicates.add(criteriaBuilder.or(predicate));
    }

    public EntityQuery<E> orPredicate(String path, Object value) {
        Predicate predicate = criteriaBuilder.equal(root.get(path), value);
        predicates.add(criteriaBuilder.or(predicate));
        return this;
    }

    public Ipage<?> toPage() {
        int page = this.firstResult / this.maxResults;
        List<E> lists = this.list();
        return Ipage.generator(this.maxResults, this.count(), page, lists);
    }

    private <T> Path<T> toJpaPath(String stringPath) {
        String[] pathParts = StringUtils.split(stringPath, '.');
        assert pathParts != null && pathParts.length > 0 : "Path cannot be empty";
        Path<T> jpaPath = null;
        for (String eachPathPart : pathParts) {
            if (jpaPath == null) {
                jpaPath = root.get(eachPathPart);
            } else {
                jpaPath = jpaPath.get(eachPathPart);
            }
        }
        return jpaPath;
    }

    public static <T> List<T> getListOfNativeQuery(Query query, Class<T> type){
        return (List<T>) query.getResultList();
    }

    public static <T> T getFirstOfNativeQuery(Query query, Class<T> type){
        return (T) query.getSingleResult();
    }

    public Long countOrSumWithNoParams(String countSumSQL) {
        return this.countOrSumQuery(countSumSQL, null);
    }

    /* Nếu Count thì object là BigInteger, Sum thì là BigDecimal */
    public Long countOrSumQuery(String countSumSQL, Map<String, Object> params) {
        var query = entityManager.createNativeQuery(countSumSQL);
        if(params != null) {
            for (var entry : params.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        var object = query.getSingleResult();
        if( object instanceof BigInteger bigInteger) {
            return Optional.of(bigInteger).map(BigInteger::longValue).orElse(0L);
        }
        BigDecimal result = (BigDecimal) object;
        return Optional.ofNullable(result).map(BigDecimal::longValue).orElse(0L);
    }
}
