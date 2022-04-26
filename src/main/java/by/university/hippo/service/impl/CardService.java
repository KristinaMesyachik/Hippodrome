package by.university.hippo.service.impl;

import by.university.hippo.entity.Card;
import by.university.hippo.entity.User;
import by.university.hippo.exception.NoSuchHippoException;
import by.university.hippo.repository.ICardRepository;
import by.university.hippo.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardService implements IService<Card, Long> {

    @Autowired
    private ICardRepository cardRepository;

    @Autowired
    private UserService userService;

    @Override
    public List<Card> findAll() {
        return cardRepository.findAll();
    }

    @Override
    public Card findById(Long id) {
        Optional<Card> cardOptional = cardRepository.findById(id);
        if (cardOptional.isEmpty()) {
            throw new NoSuchHippoException("There is no card with ID = " + id + "in database");
        }
        return cardOptional.get();
    }

    @Override
    public void delete(Long id) {
        Optional<Card> cardOptional = cardRepository.findById(id);
        if (cardOptional.isEmpty()) {
            throw new NoSuchHippoException("There is no card with ID = " + id + "in database");
        } else if (cardOptional.get().getEnabled() == 1) {
            cardOptional.get().setEnabled(0);
        } else {
            cardOptional.get().setEnabled(1);
        }
        cardRepository.save(cardOptional.get());
    }

//    @Override
    public void save(Card entity, String username) {
        User user = userService.findByLogin(username);
        entity.setUserId(user.getId());
        cardRepository.save(entity);
    }
}
