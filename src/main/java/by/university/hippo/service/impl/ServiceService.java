package by.university.hippo.service.impl;

import by.university.hippo.DTO.ServiceDTO;
import by.university.hippo.entity.Service;
import by.university.hippo.exception.NoSuchHippoException;
import by.university.hippo.repository.IServiceRepository;
import by.university.hippo.service.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class ServiceService implements IServiceService {

    @Autowired
    private IServiceRepository serviceRepository;

    @Autowired
    private IHorseService horseService;

    @Autowired
    private IStaffService staffService;

    @Autowired
    private IPriceListService priceListService;

    @Override
    public List<ServiceDTO> findAll() {
        return serviceRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
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
    public ServiceDTO findByIdDTO(Long id) {
        return mapToDTO(findById(id));
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
        Service service = findById(id);
        if (service.getEnabled() == 0) {
            service.setEnabled(1);
        } else {
            service.setEnabled(0);
        }
        save(service);
    }

    @Override
    public void save(ServiceDTO dto) {
        save(mapToEntity(dto));
    }

    @Transactional
    public List<Service> save(HashMap<Integer, ServiceDTO> hashMap) {
        List<ServiceDTO> services = new ArrayList<>(hashMap.values());
        List<Service> serviceList = mapListToEntity(services);
        boolean flag = false;
        List<Service> wrongService = new ArrayList<>();
        for (Service s : serviceList) {
            s.setId(null);
            Service byAllArgs = serviceRepository.findByTimeAndPlaceAndPriceList(s.getTime(), s.getPlace(), s.getPriceList());
            if (byAllArgs != null) {
                flag = true;
                wrongService.add(byAllArgs);
            }
        }
        if (!flag) {
            for (Service s : serviceList) {
                save(s);
            }
        }
        return wrongService;
    }

    public List<ServiceDTO> findByArgsList(HashMap<Integer, ServiceDTO> hashMap) {
        List<ServiceDTO> services = new ArrayList<>(hashMap.values());
        List<Service> serviceList = mapListToEntity(services);
        List<Service> serviceArrayList = new ArrayList<>();
        for (Service s : serviceList) {
            s.setId(null);
            Service byAllArgs = serviceRepository.findByTimeAndPlaceAndPriceList(s.getTime(), s.getPlace(), s.getPriceList());
            serviceArrayList.add(byAllArgs);
        }
        return mapListToDTO(serviceArrayList);
    }

    @Override
    public void save(Service entity) {
        serviceRepository.save(entity);
    }

    @Override
    public ServiceDTO mapToDTO(Service entity) {
        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setId(entity.getId());
        serviceDTO.setPlace(entity.getPlace());
        serviceDTO.setPriceList(priceListService.mapToDTO(entity.getPriceList()));
        if (entity.getEnabled() == 0) {
            serviceDTO.setEnabled("Блокировано");
        } else {
            serviceDTO.setEnabled("Активно");
        }
        serviceDTO.setTime(entity.getTime().toLocalTime().toString());
        serviceDTO.setDate(entity.getTime().toLocalDate().toString());
        if (entity.getHorses() != null && entity.getStaff() != null) {
            serviceDTO.setHorses(horseService.mapListToDTO(entity.getHorses()));
            serviceDTO.setStaff(staffService.mapListToDTO(entity.getStaff()));
        }
        return serviceDTO;
    }

    @Override
    public List<ServiceDTO> mapListToDTO(List<Service> list) {
        return list.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Service> mapListToEntity(List<ServiceDTO> dto) {
        return dto.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Service mapToEntity(ServiceDTO dto) {
        Service service = new Service();
        service.setId(dto.getId());
        service.setPlace(dto.getPlace());
        if (Objects.equals(dto.getEnabled(), "Блокировано")) {
            service.setEnabled(0);
        } else {
            service.setEnabled(1);
        }
        service.setPriceList(priceListService.mapToEntity(dto.getPriceList()));
        service.setTime(LocalDateTime.parse(dto.getDate() + "T" + dto.getTime()));
        if (dto.getHorses() != null && dto.getStaff() != null) {
            service.setHorses(horseService.mapListToEntity(dto.getHorses()));
            service.setStaff(staffService.mapListToEntity(dto.getStaff()));
        }
        return service;
    }
}
