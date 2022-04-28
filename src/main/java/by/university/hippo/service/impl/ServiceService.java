package by.university.hippo.service.impl;

import by.university.hippo.entity.Service;
import by.university.hippo.exception.NoSuchHippoException;
import by.university.hippo.repository.IServiceRepository;
import by.university.hippo.service.IService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceService implements IService<Service, Long> {

    @Autowired
    private IServiceRepository serviceRepository;

    @Override
    public List<Service> findAll() {
        return serviceRepository.findAll();
    }

    @Override
    public Service findById(Long id) {
        Optional<Service> serviceOptional = serviceRepository.findById(id);
        if (serviceOptional.isEmpty()) {
            throw new NoSuchHippoException("There is no service with ID = " + id + "in database");
        }
        return serviceOptional.get();
    }

    @Override
    public void delete(Long id) {
        Service service = findById(id);
        if (service.getEnabled() == 1) {
            service.setEnabled(0);
        } else {
            service.setEnabled(1);
        }
        save(service);
    }

    //    @Override
    public void save(Service entity) {
        serviceRepository.save(entity);
    }

    public List<Long> addFromBasket(Long id, List<Long> basket) {
        basket.add(id);
        return basket;
    }

    public List<Long> deleteFromBasket(Long id, List<Long> basket) {
        basket.remove(id);
        return basket;
    }

    @Transactional
    public List<Service> viewBasket(List<Long> basket) {
        List<Service> services = new ArrayList<>();
        for (Long i : basket) {
            services.add(findById(i));
        }
        return services;
    }
}
