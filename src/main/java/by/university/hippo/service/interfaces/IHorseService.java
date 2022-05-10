package by.university.hippo.service.interfaces;

import by.university.hippo.DTO.HorseDTO;
import by.university.hippo.entity.Horse;
import by.university.hippo.service.IService;

import java.util.List;

public interface IHorseService extends IService<Horse, Long, HorseDTO> {
    List<HorseDTO> findByEnabledIs();

    void save(HorseDTO dto);

    void save(Horse entity);
}
