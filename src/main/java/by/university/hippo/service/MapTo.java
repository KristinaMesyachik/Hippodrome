package by.university.hippo.service;

public interface MapTo<T, K> {
    T mapToDTO(K entity);
    K mapToEntity(T dto);
}
