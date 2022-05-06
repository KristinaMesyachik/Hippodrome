package by.university.hippo.service.impl;

import by.university.hippo.DTO.AboutServiceDTO;
import by.university.hippo.entity.AboutService;
import by.university.hippo.exception.NoSuchHippoException;
import by.university.hippo.repository.IAboutServiceRepository;
import by.university.hippo.service.interfaces.IAboutServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AboutServiceService implements IAboutServiceService {

    @Autowired
    private IAboutServiceRepository aboutServiceRepository;

    @Override
    public List<AboutServiceDTO> findAll() {
        return aboutServiceRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AboutService findById(Long id) {
        Optional<AboutService> aboutServiceOptional = aboutServiceRepository.findById(id);
        if (aboutServiceOptional.isEmpty()) {
            throw new NoSuchHippoException("There is no about_service with ID = " + id + "in database");
        }
        return aboutServiceOptional.get();
    }

    @Override
    public AboutServiceDTO findByIdDTO(Long id) {
        return mapToDTO(findById(id));
    }

    @Override
    public void delete(Long id) {
        AboutService aboutService = findById(id);
        if (aboutService.getEnabled() == 0) {
            aboutService.setEnabled(1);
        } else {
            aboutService.setEnabled(0);
        }
        save(aboutService);
    }

    @Override
    public void save(AboutServiceDTO dto) {
        save(mapToEntity(dto));
    }

    @Override
    public void save(AboutService entity) {
        aboutServiceRepository.save(entity);
    }

    @Override
    public AboutServiceDTO mapToDTO(AboutService entity) {
        AboutServiceDTO aboutServiceDTO = new AboutServiceDTO();
        aboutServiceDTO.setId(entity.getId());
        aboutServiceDTO.setTitle(entity.getTitle());
        aboutServiceDTO.setDescription(entity.getDescription());
        aboutServiceDTO.setCost(entity.getCost());
        if (entity.getEnabled() == 0) {
            aboutServiceDTO.setEnabled("Блокировано");
        } else {
            aboutServiceDTO.setEnabled("Активно");
        }
        return aboutServiceDTO;
    }

    @Override
    public AboutService mapToEntity(AboutServiceDTO dto) {
        AboutService aboutService = new AboutService();
        aboutService.setId(dto.getId());
        aboutService.setTitle(dto.getTitle());
        aboutService.setDescription(dto.getDescription());
        aboutService.setCost(dto.getCost());
        if (Objects.equals(dto.getEnabled(), "Блокировано")) {
            aboutService.setEnabled(0);
        } else {
            aboutService.setEnabled(1);
        }
        return aboutService;
    }

    @Override
    public List<AboutServiceDTO> mapListToDTO(List<AboutService> list) {
        return null;
    }

    @Override
    public List<AboutService> mapListToEntity(List<AboutServiceDTO> dto) {
        return null;
    }
}
