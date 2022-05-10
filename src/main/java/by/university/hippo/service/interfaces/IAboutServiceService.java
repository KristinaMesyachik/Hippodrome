package by.university.hippo.service.interfaces;

import by.university.hippo.DTO.AboutServiceDTO;
import by.university.hippo.entity.AboutService;
import by.university.hippo.service.IService;

import java.util.List;

public interface IAboutServiceService extends IService<AboutService, Long, AboutServiceDTO> {
    List<AboutServiceDTO> findByEnabledIs();

    void save(AboutService entity);

    void save(AboutServiceDTO entity);
}
