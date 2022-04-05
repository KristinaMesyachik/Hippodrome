package by.university.hippo.controller;

import by.university.hippo.entity.Card;
import by.university.hippo.service.impl.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public List<Card> findAll(Model model) {
        List<Card> cards = cardService.findAll();
        return cards;
    }
}
