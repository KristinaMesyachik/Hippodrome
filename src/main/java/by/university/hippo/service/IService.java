package by.university.hippo.service;

import java.util.List;

public interface IService<D, K>{
    List<D> findAll();

    D findById(K id);

    void delete(K id);

}
