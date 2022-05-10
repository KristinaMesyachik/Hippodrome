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
    public CardDTO findByIdDTO(Long id) {
        return mapToDTO(findById(id));
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
        cardRepository.deleteById(id);
    }

    @Override
    public void save(CardDTO entity, String username) {
        User user = userService.findByLogin(username);
        Card card = mapToEntity(entity);
        cardRepository.save(card);
        user.setCard(card);
    }

    @Override
    public CardDTO mapToDTO(Card entity) {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setId(entity.getId());
        cardDTO.setNumber(entity.getNumber());
        return cardDTO;
    }

    @Override
    public Card mapToEntity(CardDTO dto) {
        Card card = new Card();
        card.setId(dto.getId());
        card.setNumber(dto.getNumber());
        return card;
    }

    @Override
    public List<CardDTO> mapListToDTO(List<Card> list) {
        return list.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Card> mapListToEntity(List<CardDTO> dto) {
        return dto.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }
}