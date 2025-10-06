package vn.flast.repositories;
/**************************************************************************/
/*  GenericRepository.java                                                */
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

import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@NoRepositoryBean
public interface GenericRepository<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    class SpecificationBuilder<T> {

        private final GenericRepository<T, ?> repository;
        private final List<Specification<T>> specifications;
        private boolean useOr = false;
        private SpecificationBuilder<T> currentGroup = null;

        @SuppressWarnings("rawtypes")
        private final Map<String, Join> joins;

        private SpecificationBuilder(GenericRepository<T, ?> repository) {
            this.repository = repository;
            this.specifications = new ArrayList<>();
            this.joins = new HashMap<>();
        }

        public SpecificationBuilder<T> join(String attribute) {
            joins.computeIfAbsent(attribute, key -> ((Root<?>) getCurrentRoot()).join(key, JoinType.LEFT));
            return this;
        }

        private Object getCurrentRoot() {
            return currentGroup != null ? currentGroup : this;
        }

        public SpecificationBuilder<T> isEqual(String fieldName, Object value) {
            if(Objects.isNull(value)) {
                return this;
            }
            Specification<T> spec = (root, query, cb) -> cb.equal(getFieldPath(root, fieldName), value);
            addToCurrentScope(spec);
            return this;
        }

        public SpecificationBuilder<T> like(String fieldName, String value) {
            if(Objects.isNull(value)) {
                return this;
            }
            Specification<T> spec = (root, query, cb) -> cb.like(cb.lower(getFieldPath(root, fieldName)), "%" + value.toLowerCase() + "%");
            addToCurrentScope(spec);
            return this;
        }

        public <V extends Comparable<? super V>> SpecificationBuilder<T> greaterThan(String fieldName, V value) {
            Specification<T> spec = (root, query, cb) -> {
                if (value == null) {
                    return cb.conjunction();
                }
                return cb.greaterThan(getFieldPath(root, fieldName), value);
            };
            addToCurrentScope(spec);
            return this;
        }

        public <V extends Comparable<? super V>> SpecificationBuilder<T> lessThan(String fieldName, V value) {
            Specification<T> spec = (root, query, cb) -> {
                if (value == null) {
                    return cb.conjunction();
                }
                return cb.lessThan(getFieldPath(root, fieldName), value);
            };
            addToCurrentScope(spec);
            return this;
        }

        public <V extends Comparable<? super V>> SpecificationBuilder<T> between(String fieldName, V min, V max) {
            Specification<T> spec = (root, query, cb) -> {
                if (min == null || max == null) {
                    return cb.conjunction();
                }
                Path<V> path = getFieldPath(root, fieldName);
                return cb.between(path, min, max);
            };
            addToCurrentScope(spec);
            return this;
        }

        public SpecificationBuilder<T> in(String fieldName, Iterable<?> values) {
            Specification<T> spec = (root, query, cb) -> {
                if (values == null) {
                    return cb.conjunction();
                }
                return getFieldPath(root, fieldName).in(values);
            };
            addToCurrentScope(spec);
            return this;
        }

        public SpecificationBuilder<T> isNull(String fieldName) {
            Specification<T> spec = (root, query, cb) -> cb.isNull(getFieldPath(root, fieldName));
            addToCurrentScope(spec);
            return this;
        }

        public SpecificationBuilder<T> isNotNull(String fieldName) {
            Specification<T> spec = (root, query, cb) -> cb.isNotNull(getFieldPath(root, fieldName));
            addToCurrentScope(spec);
            return this;
        }

        public SpecificationBuilder<T> notEqual(String fieldName, Object value) {
            Specification<T> spec = (root, query, cb) -> {
                if (value == null) {
                    return cb.isNotNull(getFieldPath(root, fieldName));
                }
                return cb.notEqual(getFieldPath(root, fieldName), value);
            };
            addToCurrentScope(spec);
            return this;
        }

        public SpecificationBuilder<T> or() {
            this.useOr = true;
            if (currentGroup != null) {
                currentGroup.useOr = true;
            }
            return this;
        }

        public SpecificationBuilder<T> group() {
            if (currentGroup != null) {
                throw new IllegalStateException("Nested groups are not supported");
            }
            currentGroup = new SpecificationBuilder<>(repository);
            currentGroup.joins.putAll(this.joins); /* Kế thừa các join từ parent */
            return this;
        }

        public SpecificationBuilder<T> endGroup() {
            if (currentGroup == null) {
                throw new IllegalStateException("No group to end");
            }
            specifications.add(currentGroup.buildSpecification());
            currentGroup = null;
            return this;
        }

        private void addToCurrentScope(Specification<T> spec) {
            if (currentGroup != null) {
                currentGroup.specifications.add(spec);
            } else {
                specifications.add(spec);
            }
        }

        @SuppressWarnings("unchecked")
        private <V> Path<V> getFieldPath(Root<T> root, String fieldName) {
            String[] parts = fieldName.split("\\.");
            Path<?> path = root;
            for (String part : parts) {
                if (joins.containsKey(part)) {
                    path = joins.get(part);
                } else {
                    path = path.get(part);
                }
            }
            return (Path<V>) path;
        }

        private Specification<T> buildSpecification() {
            if (currentGroup != null) {
                throw new IllegalStateException("Unclosed group detected");
            }
            if (specifications.isEmpty()) {
                return (root, query, cb) -> cb.conjunction();
            }
            return (root, query, cb) -> {
                /* Áp dụng các join trước khi tạo predicate */
                joins.forEach((attribute, join) -> root.join(attribute, JoinType.LEFT));
                Predicate[] predicates = specifications.stream()
                    .map(spec -> spec.toPredicate(root, query, cb))
                    .toArray(Predicate[]::new);
                return useOr ? cb.or(predicates) : cb.and(predicates);
            };
        }

        public List<T> findAll() {
            return repository.findAll(buildSpecification());
        }

        public Optional<T> findOne() {
            return repository.findOne(buildSpecification());
        }

        public List<T> findAll(int offset, int limit) {
            return repository.findAll(buildSpecification(), repository.createPageRequest(offset, limit)).getContent();
        }

        public List<T> findAll(int offset, int limit, Sort sort) {
            return repository.findAll(buildSpecification(), repository.createPageRequest(offset, limit, sort)).getContent();
        }

        public Page<T> toPage(int offset, int limit, Sort sort) {
            if (offset < 0 || limit <= 0) {
                throw new IllegalArgumentException("Offset must be non-negative and limit must be positive");
            }
            Pageable pageable = PageRequest.of(offset / limit, limit, sort);
            return repository.findAll(buildSpecification(), pageable);
        }

        public long countItem() {
            return repository.count(buildSpecification());
        }
    }

    default SpecificationBuilder<T> isEqual(String fieldName, Object value) {
        return new SpecificationBuilder<>(this).isEqual(fieldName, value);
    }

    default SpecificationBuilder<T> like(String fieldName, String value) {
        return new SpecificationBuilder<>(this).like(fieldName, value);
    }

    default <V extends Comparable<? super V>> SpecificationBuilder<T> greaterThan(String fieldName, V value) {
        return new SpecificationBuilder<>(this).greaterThan(fieldName, value);
    }

    default <V extends Comparable<? super V>> SpecificationBuilder<T> lessThan(String fieldName, V value) {
        return new SpecificationBuilder<>(this).lessThan(fieldName, value);
    }

    default <V extends Comparable<? super V>> SpecificationBuilder<T> between(String fieldName, V min, V max) {
        return new SpecificationBuilder<>(this).between(fieldName, min, max);
    }

    default SpecificationBuilder<T> in(String fieldName, Iterable<?> values) {
        return new SpecificationBuilder<>(this).in(fieldName, values);
    }

    default SpecificationBuilder<T> isNull(String fieldName) {
        return new SpecificationBuilder<>(this).isNull(fieldName);
    }

    default SpecificationBuilder<T> isNotNull(String fieldName) {
        return new SpecificationBuilder<>(this).isNotNull(fieldName);
    }

    default SpecificationBuilder<T> notEqual(String fieldName, Object value) {
        return new SpecificationBuilder<>(this).notEqual(fieldName, value);
    }

    default SpecificationBuilder<T> join(String attribute) {
        return new SpecificationBuilder<>(this).join(attribute);
    }

    default PageRequest createPageRequest(int offset, int limit) {
        if (offset < 0 || limit <= 0) {
            throw new IllegalArgumentException("Offset must be non-negative and limit must be positive");
        }
        return PageRequest.of(offset / limit, limit);
    }

    default PageRequest createPageRequest(int offset, int limit, Sort sort) {
        if (offset < 0 || limit <= 0) {
            throw new IllegalArgumentException("Offset must be non-negative and limit must be positive");
        }
        return PageRequest.of(offset / limit, limit, sort);
    }
}
