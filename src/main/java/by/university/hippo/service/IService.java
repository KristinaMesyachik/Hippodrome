package by.university.hippo.service;

import java.util.List;

public interface IService<T, K, D> extends MapTo<D, T> {
    List<D> findAll();

    T findById(K id);

    D findByIdDTO(K id);

    void delete(K id);
}
