package by.university.hippo.service.interfaces;

import by.university.hippo.DTO.CardDTO;
import by.university.hippo.entity.Card;
import by.university.hippo.service.IService;

public interface ICardService extends IService<Card, Long, CardDTO> {
    Card findByIdCard(Long id);

    void save(CardDTO entity, String username);
}