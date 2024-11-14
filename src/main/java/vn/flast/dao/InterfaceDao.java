package vn.flast.dao;

import java.util.List;

public interface InterfaceDao <K, E> {
    void flush();
    void persist(E entity);
    E merge(E entity);
    void remove(E entity);
    E findById(K id);
    List<E> findAll();
    void rollBack();
}
