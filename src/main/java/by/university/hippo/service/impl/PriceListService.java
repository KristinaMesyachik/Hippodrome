package by.university.hippo.service.impl;

import by.university.hippo.DTO.PriceListDTO;
import by.university.hippo.entity.PriceList;
import by.university.hippo.exception.NoSuchHippoException;
import by.university.hippo.repository.IPriceListRepository;
import by.university.hippo.service.interfaces.IAboutServiceService;
import by.university.hippo.service.interfaces.IPriceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PriceListService implements IPriceListService {

    @Autowired
    private IPriceListRepository priceListRepository;

    @Autowired
    private IAboutServiceService aboutServiceService;

    @Override
    public List<PriceListDTO> findAll() {
        return priceListRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PriceList findById(Long id) {
        Optional<PriceList> priceListOptional = priceListRepository.findById(id);
        if (priceListOptional.isEmpty()) {
            throw new NoSuchHippoException("There is no service with ID = " + id + "in database");
        }
        return priceListOptional.get();
    }

    @Override
    public PriceListDTO findByIdDTO(Long id) {
        return mapToDTO(findById(id));
    }

    @Override
    public void delete(Long id) {
        PriceList priceList = findById(id);
        if (priceList.getEnabled() == 0) {
            priceList.setEnabled(1);
        } else {
            priceList.setEnabled(0);
        }
        save(priceList);
    }

    @Override
    public PriceListDTO mapToDTO(PriceList entity) {
        PriceListDTO priceListDTO = new PriceListDTO();
        priceListDTO.setId(entity.getId());
        priceListDTO.setAmount(entity.getAmount());
        priceListDTO.setCountOfPeople(entity.getCountOfPeople());
        priceListDTO.setDuration(entity.getDuration());
        priceListDTO.setAboutService(aboutServiceService.mapToDTO(entity.getAboutService()));
        if (entity.getEnabled() == 0) {
            priceListDTO.setEnabled("Блокировано");
        } else {
            priceListDTO.setEnabled("Активно");
        }
        return priceListDTO;
    }

    @Override
    public PriceList mapToEntity(PriceListDTO dto) {
        PriceList priceList = new PriceList();
        priceList.setId(dto.getId());
        priceList.setAmount(dto.getAmount());
        priceList.setAboutService(aboutServiceService.mapToEntity(dto.getAboutService()));
        priceList.setCountOfPeople(dto.getCountOfPeople());
        priceList.setDuration(dto.getDuration());
        if (Objects.equals(dto.getEnabled(), "Блокировано")) {
            priceList.setEnabled(0);
        } else {
            priceList.setEnabled(1);
        }
        return priceList;
    }

    @Override
    public List<PriceListDTO> mapListToDTO(List<PriceList> list) {
        return list.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PriceList> mapListToEntity(List<PriceListDTO> dto) {
        return dto.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<PriceListDTO> findByEnabledIs() {
        return priceListRepository.findByEnabledIs(1).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void save(PriceListDTO dto) {
        save(mapToEntity(dto));
    }

    @Override
    public void save(PriceList entity) {
        priceListRepository.save(entity);
    }
}
