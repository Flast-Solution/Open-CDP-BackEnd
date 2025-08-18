package vn.flast.dao;
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
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public abstract class DaoImpl<K, E> implements InterfaceDao<K, E>{

    protected Class<E> entityClass;

    @PersistenceContext
    protected EntityManager entityManager;

    @Transactional
    public void flush() {
        entityManager.flush();
    }

    public DaoImpl(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    @Transactional
    public E persist(E entity) {
        entityManager.persist(entity);
        return entity;
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
