package by.university.hippo.service.interfaces;

import by.university.hippo.DTO.ServiceDTO;
import by.university.hippo.entity.Service;
import by.university.hippo.service.IService;

import java.util.List;

public interface IServiceService extends IService<Service, Long, ServiceDTO> {

    List<ServiceDTO> findByEnabledIs();

    void save(ServiceDTO dto);

    void save(Service entity);
}
