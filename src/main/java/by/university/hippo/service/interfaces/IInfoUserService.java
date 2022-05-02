package by.university.hippo.service.interfaces;

import by.university.hippo.DTO.InfoUserDTO;
import by.university.hippo.entity.InfoUser;
import by.university.hippo.service.IService;

public interface IInfoUserService extends IService<InfoUser, Long, InfoUserDTO> {
    InfoUser findByIdInfo(Long id);

    void save(InfoUser entity);
}