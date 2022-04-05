package by.university.hippo.service.impl;

import by.university.hippo.entity.Card;
import by.university.hippo.repository.ICardRepository;
import by.university.hippo.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService implements IService<Card, Long> {

    @Autowired
    private ICardRepository cardRepository;

    @Override
    public List<Card> findAll() {
        return cardRepository.findAll();
    }

    @Override
    public Card findById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void save(Card entity) {
    }
}
