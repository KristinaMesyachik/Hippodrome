package by.university.hippo.controller;

import by.university.hippo.DTO.CardDTO;
import by.university.hippo.DTO.UserDTO;
import by.university.hippo.service.impl.CardService;
import by.university.hippo.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Autowired
    private UserService userService;


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/admin"}, method = RequestMethod.GET)
    public String findAll(Model model) {
        List<CardDTO> cards = cardService.findAll();
        model.addAttribute("cards", cards);
        return "all-cards";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String findAllUser(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        UserDTO user = userService.findByLoginDTO(username);
        model.addAttribute("cards", user.getCard());
        return "all-cards";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/addCard"}, method = RequestMethod.GET)
    public String showAddCardPage(Model model) {
        CardDTO card = new CardDTO();
        model.addAttribute("card", card);
        return "addCard";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/save"}, method = RequestMethod.POST)
    public String save(@ModelAttribute(name = "card") CardDTO card
            , HttpSession session) {
        String username = (String) session.getAttribute("username");
        cardService.save(card, username);
        return "redirect:/api/cards/";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/update"}, method = RequestMethod.GET)
    public String update(@RequestParam(name = "cardId") Long cardId, Model model) {
        CardDTO card = cardService.findByIdDTO(cardId);
        model.addAttribute("card", card);
        return "addCard";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/delete"}, method = RequestMethod.GET)
    public String delete(@RequestParam(name = "cardId") Long cardId) {
        cardService.delete(cardId);
        return "redirect:/api/cards/";
    }
}
