package by.university.hippo.service;

import java.util.List;

public interface MapTo<T, K> {
    T mapToDTO(K entity);

    K mapToEntity(T dto);

    List<T> mapListToDTO(List<K> list);

    List<K> mapListToEntity(List<T> dto);
}
