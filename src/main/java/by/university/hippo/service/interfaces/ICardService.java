package by.university.hippo.service.interfaces;

import by.university.hippo.DTO.CardDTO;
import by.university.hippo.entity.Card;
import by.university.hippo.service.IService;

public interface ICardService extends IService<Card, Long, CardDTO> {
    void save(CardDTO entity, String username);
}