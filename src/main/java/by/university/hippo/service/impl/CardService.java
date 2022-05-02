package by.university.hippo.service.impl;

import by.university.hippo.DTO.CardDTO;
import by.university.hippo.entity.Card;
import by.university.hippo.entity.User;
import by.university.hippo.exception.NoSuchHippoException;
import by.university.hippo.repository.ICardRepository;
import by.university.hippo.service.interfaces.ICardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardService implements ICardService {

    @Autowired
    private ICardRepository cardRepository;

    @Autowired
    private UserService userService;

    @Override
    public List<CardDTO> findAll() {
        return cardRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CardDTO findById(Long id) {
        return mapToDTO(findByIdCard(id));
    }

    @Override
    public Card findByIdCard(Long id) {
        Optional<Card> cardOptional = cardRepository.findById(id);
        if (cardOptional.isEmpty()) {
            throw new NoSuchHippoException("There is no card with ID = " + id + "in database");
        }
        return cardOptional.get();
    }

    @Override
    public void delete(Long id) {
        Card card = findByIdCard(id);
        if (card.getEnabled() == 1) {
            card.setEnabled(0);
        } else {
            card.setEnabled(1);
        }
        cardRepository.save(card);
    }

    @Override
    public void save(CardDTO entity, String username) {
        User user = userService.findByLoginUser(username);
        entity.setUserId(user.getId());
        cardRepository.save(mapToEntity(entity));
    }

    @Override
    public CardDTO mapToDTO(Card entity) {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setId(entity.getId());
        cardDTO.setNumber(entity.getNumber());
        cardDTO.setBalance(entity.getBalance());
        cardDTO.setUserId(entity.getUserId());
        if (entity.getEnabled() == 0) {
            cardDTO.setEnabled("Блокировано");
        } else {
            cardDTO.setEnabled("Активно");
        }
        return cardDTO;
    }

    @Override
    public Card mapToEntity(CardDTO dto) {
        Card card = new Card();
        card.setId(dto.getId());
        card.setNumber(dto.getNumber());
        card.setBalance(dto.getBalance());
        card.setUserId(dto.getUserId());
        if (Objects.equals(dto.getEnabled(), "Блокировано")) {
            card.setEnabled(0);
        } else {
            card.setEnabled(1);
        }
        return card;
    }
}