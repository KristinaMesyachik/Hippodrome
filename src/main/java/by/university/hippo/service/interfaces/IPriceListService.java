package by.university.hippo.service.interfaces;

import by.university.hippo.DTO.PriceListDTO;
import by.university.hippo.entity.PriceList;
import by.university.hippo.service.IService;

import java.util.List;

public interface IPriceListService extends IService<PriceList, Long, PriceListDTO> {
    List<PriceListDTO> findByEnabledIs();

    void save(PriceListDTO dto);

    void save(PriceList entity);
}
