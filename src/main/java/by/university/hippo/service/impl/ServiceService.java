package by.university.hippo.service.impl;

import by.university.hippo.DTO.ServiceDTO;
import by.university.hippo.entity.Service;
import by.university.hippo.exception.NoSuchHippoException;
import by.university.hippo.repository.IServiceRepository;
import by.university.hippo.service.interfaces.IServiceService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class ServiceService implements IServiceService {

    @Autowired
    private IServiceRepository serviceRepository;

    @Override
    public List<ServiceDTO> findAll() {
        return serviceRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ServiceDTO findById(Long id) {
        Optional<Service> serviceOptional = serviceRepository.findById(id);
        if (serviceOptional.isEmpty()) {
            throw new NoSuchHippoException("There is no service with ID = " + id + "in database");
        }
        return mapToDTO(serviceOptional.get());
    }

    @Override
    @Transactional
    public List<ServiceDTO> findByEnabledIs() {
        return serviceRepository.findByEnabledIs(1).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        ServiceDTO service = findById(id);
        if (Objects.equals(service.getEnabled(), "Блокировано")) {
            service.setEnabled("Активно");
        } else {
            service.setEnabled("Блокировано");
        }
        save(service);
    }//TODO del check

    @Override
    public void save(ServiceDTO entity) {
        serviceRepository.save(mapToEntity(entity));
    }

    @Override
    public List<Long> addFromBasket(Long id, List<Long> basket) {
        basket.add(id);
        return basket;
    }

    @Override
    public List<Long> deleteFromBasket(Long id, List<Long> basket) {
        basket.remove(id);
        return basket;
    }

    @Override
    @Transactional
    public List<ServiceDTO> viewBasket(List<Long> basket) {
        List<ServiceDTO> services = new ArrayList<>();
        for (Long i : basket) {
            services.add(findById(i));
        }
        return services;
    }

    @Override
    public ServiceDTO mapToDTO(Service entity) {
        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setId(entity.getId());
        serviceDTO.setTitle(entity.getTitle());
        serviceDTO.setDescription(entity.getDescription());
        serviceDTO.setCost(entity.getCost());
        serviceDTO.setPlace(entity.getPlace());
        if (entity.getEnabled() == 0) {
            serviceDTO.setEnabled("Блокировано");
        } else {
            serviceDTO.setEnabled("Активно");
        }
        serviceDTO.setTime(entity.getTime().toLocalTime().toString());
        serviceDTO.setDate(entity.getTime().toLocalDate().toString());
//        serviceDTO.setHorses(entity.getHorses());
//        serviceDTO.setStaff(entity.getStaff());
        return serviceDTO;
    }

    @Override
    public Service mapToEntity(ServiceDTO dto) {
        Service service = new Service();
        service.setId(dto.getId());
        service.setTitle(dto.getTitle());
        service.setDescription(dto.getDescription());
        service.setCost(dto.getCost());
        service.setPlace(dto.getPlace());
        if (Objects.equals(dto.getEnabled(), "Блокировано")) {
            service.setEnabled(0);
        } else {
            service.setEnabled(1);
        }
        service.setTime(LocalDateTime.parse(dto.getDate() + "T" + dto.getTime()));//TODO
//        service.setHorses(dto.getHorses());
//        service.setStaff(dto.getStaff());
        return service;
    }
}
