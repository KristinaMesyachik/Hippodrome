package by.university.hippo.service.impl;

import by.university.hippo.entity.Service;
import by.university.hippo.repository.IServiceRepository;
import by.university.hippo.service.IService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceService implements IService<Service,Long> {

    @Autowired
    private IServiceRepository serviceRepository;

    @Override
    public List<Service> findAll() {
        return serviceRepository.findAll();
    }

    @Override
    public Service findById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void save(Service entity) {

    }
}
