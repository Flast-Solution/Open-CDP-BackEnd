package vn.flast.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class DaoImpl<K, E> implements InterfaceDao<K, E>{

    protected Class<E> entityClass;

    @PersistenceContext
    protected EntityManager entityManager;

    @Transactional
    public void flush() {
        entityManager.flush();
    }

    public DaoImpl() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[1];
    }

    @Transactional
    public void persist(E entity) {
        entityManager.persist(entity);
    }

    @Transactional
    public E merge(E entity) {
        entityManager.merge(entity);
        return entity;
    }

    @Transactional
    public void remove(E entity) {
        entityManager.remove(entity);
    }

    public E findById(K id) {
        return entityManager.find(entityClass, id);
    }

    public void rollBack() {
        entityManager.getTransaction().rollback();
    }

    public List<E> findAll() {
        String query = "from " + entityClass.getName() + " c";
        return entityManager.createQuery(query, entityClass).getResultList();
    }
}
