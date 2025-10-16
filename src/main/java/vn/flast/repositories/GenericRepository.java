package vn.flast.repositories;

import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import vn.flast.pagination.Ipage;
import java.util.*;

@NoRepositoryBean
public interface GenericRepository<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    class SpecificationBuilder<T> {

        private final GenericRepository<T, ?> repository;
        private final List<Specification<T>> specifications;
        private boolean useOr = false;
        private SpecificationBuilder<T> currentGroup = null;

        private final List<String> joinAttributes;
        private final List<String> fetchAttributes;

        private SpecificationBuilder(GenericRepository<T, ?> repository) {
            this.repository = repository;
            this.specifications = new ArrayList<>();
            this.joinAttributes = new ArrayList<>();
            this.fetchAttributes = new ArrayList<>();
        }

        public SpecificationBuilder<T> join(String attribute) {
            joinAttributes.add(attribute);
            return this;
        }

        public SpecificationBuilder<T> fetch(String attribute) {
            fetchAttributes.add(attribute);
            return this;
        }

        public SpecificationBuilder<T> isEqual(String fieldName, Object value) {
            if (Objects.isNull(value)) {
                return this;
            }
            Specification<T> spec = (root, query, cb) -> cb.equal(getFieldPath(root, fieldName), value);
            addToCurrentScope(spec);
            return this;
        }

        public SpecificationBuilder<T> like(String fieldName, String value) {
            if (Objects.isNull(value)) {
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
            currentGroup.joinAttributes.addAll(this.joinAttributes);
            currentGroup.fetchAttributes.addAll(this.fetchAttributes);
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
                if (joinAttributes.contains(part)) {
                    path = ((Root<?>) path).join(part, JoinType.LEFT);
                } else if (fetchAttributes.contains(part)) {
                    path = ((Root<?>) path).join(part, JoinType.LEFT);
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
            if (specifications.isEmpty() && joinAttributes.isEmpty() && fetchAttributes.isEmpty()) {
                return (root, query, cb) -> cb.conjunction();
            }
            return (root, query, cb) -> {
                /* Apply joins */
                @SuppressWarnings("rawtypes")
                Map<String, Join> joins = new HashMap<>();
                for (String attribute : joinAttributes) {
                    joins.computeIfAbsent(attribute, key -> root.join(key, JoinType.LEFT));
                }
                /* Apply fetches */
                for (String attribute : fetchAttributes) {
                    root.fetch(attribute, JoinType.LEFT);
                }
                /* Build predicates */
                Predicate[] predicates = specifications.stream()
                    .map(spec -> spec.toPredicate(root, query, cb))
                    .filter(Objects::nonNull)
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

        public Ipage<T> toPage(int offset, int limit, Sort sort) {
            if (offset < 0 || limit <= 0) {
                throw new IllegalArgumentException("Offset must be non-negative and limit must be positive");
            }
            Pageable pageable = PageRequest.of(offset / limit, limit, sort);
            Page<T> page = repository.findAll(buildSpecification(), pageable);
            return Ipage.generator(limit, page.getTotalElements(), page.getNumber(), page.getContent());
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

    default SpecificationBuilder<T> fetch(String attribute) {
        return new SpecificationBuilder<>(this).fetch(attribute);
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