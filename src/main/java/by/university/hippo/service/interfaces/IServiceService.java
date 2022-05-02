package by.university.hippo.service.interfaces;

import by.university.hippo.DTO.ServiceDTO;
import by.university.hippo.entity.Service;
import by.university.hippo.service.IService;

import java.util.List;

public interface IServiceService extends IService<Service, Long, ServiceDTO> {
    List<ServiceDTO> findByEnabledIs();

    void save(ServiceDTO entity);

    List<Long> addFromBasket(Long id, List<Long> basket);

    List<Long> deleteFromBasket(Long id, List<Long> basket);

    List<ServiceDTO> viewBasket(List<Long> basket);
}
