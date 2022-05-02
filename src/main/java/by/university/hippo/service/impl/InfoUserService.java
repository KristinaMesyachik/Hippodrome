package by.university.hippo.service.impl;

import by.university.hippo.DTO.InfoUserDTO;
import by.university.hippo.entity.InfoUser;
import by.university.hippo.exception.NoSuchHippoException;
import by.university.hippo.repository.IInfoUserRepository;
import by.university.hippo.service.interfaces.IInfoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InfoUserService implements IInfoUserService {

    @Autowired
    private IInfoUserRepository infoUserRepository;

    @Override
    public List<InfoUserDTO> findAll() {
        return infoUserRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public InfoUserDTO findById(Long id) {
        return mapToDTO(findByIdInfo(id));
    }

    @Override
    public InfoUser findByIdInfo(Long id) {
        Optional<InfoUser> infoUserOptional = infoUserRepository.findById(id);
        if (infoUserOptional.isEmpty()) {
            throw new NoSuchHippoException("There is no info with ID = " + id + "in database");
        }
        return infoUserOptional.get();
    }

    @Override
    public void delete(Long id) {
        infoUserRepository.deleteById(id);
    }

    @Override
    public void save(InfoUser entity) {
        infoUserRepository.save(entity);
    }

    @Override
    public InfoUserDTO mapToDTO(InfoUser entity) {
        InfoUserDTO infoUserDTO = new InfoUserDTO();
        infoUserDTO.setId(entity.getId());
        infoUserDTO.setLastname(entity.getLastname());
        infoUserDTO.setFirstname(entity.getFirstname());
        infoUserDTO.setMiddlename(entity.getMiddlename());
        infoUserDTO.setPhone(entity.getPhone());
        infoUserDTO.setMail(entity.getMail());
        return infoUserDTO;
    }

    @Override
    public InfoUser mapToEntity(InfoUserDTO dto) {
        InfoUser infoUser = new InfoUser();
        infoUser.setId(dto.getId());
        infoUser.setLastname(dto.getLastname());
        infoUser.setFirstname(dto.getFirstname());
        infoUser.setMiddlename(dto.getMiddlename());
        infoUser.setPhone(dto.getPhone());
        infoUser.setMail(dto.getMail());
        return infoUser;
    }
}
