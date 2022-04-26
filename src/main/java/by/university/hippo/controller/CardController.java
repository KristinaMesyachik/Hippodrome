package by.university.hippo.controller;

import by.university.hippo.entity.Card;
import by.university.hippo.service.impl.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/api/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String findAll(Model model) {
        List<Card> cards = cardService.findAll();
        model.addAttribute("cards", cards);
        return "all-cards";
    }

    @RequestMapping(value = {"/addCard"}, method = RequestMethod.GET)
    public String showAddCardPage(Model model) {
        Card card = new Card();
        model.addAttribute("card", card);
        return "addCard";
    }

    @RequestMapping(value = {"/save"}, method = RequestMethod.POST)
    public String save(@ModelAttribute(name = "card") Card card
            , HttpSession session) {
        String username = (String) session.getAttribute("username");
        cardService.save(card, username);
        return "redirect:/api/cards/";
    }

    @RequestMapping(value = {"/update"}, method = RequestMethod.GET)
    public String update(@RequestParam(name = "cardId") Long cardId, Model model) {
        Card card = cardService.findById(cardId);
        model.addAttribute("card", card);
        return "addCard";
    }

    @RequestMapping(value = {"/delete"}, method = RequestMethod.GET)
    public String delete(@RequestParam(name = "cardId") Long cardId) {
        cardService.delete(cardId);
        return "redirect:/api/cards/";
    }
}
