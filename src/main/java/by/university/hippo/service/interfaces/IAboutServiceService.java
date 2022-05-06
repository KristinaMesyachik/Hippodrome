package by.university.hippo.service.interfaces;

import by.university.hippo.DTO.AboutServiceDTO;
import by.university.hippo.entity.AboutService;
import by.university.hippo.service.IService;

public interface IAboutServiceService extends IService<AboutService, Long, AboutServiceDTO> {
    void save(AboutService entity);
    void save(AboutServiceDTO entity);
}
