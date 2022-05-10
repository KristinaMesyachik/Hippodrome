package by.university.hippo.controller;

import by.university.hippo.DTO.PriceListDTO;
import by.university.hippo.DTO.UserDTO;
import by.university.hippo.service.impl.PriceListService;
import by.university.hippo.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

import static by.university.hippo.controller.ServiceController.basket;
import static by.university.hippo.controller.ServiceController.discount;

@Controller
@RequestMapping("/api/priceLists")
public class PriceListController {

    @Autowired
    private PriceListService priceListService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String findAllUser(Model model) {
        List<PriceListDTO> priceList = priceListService.findByEnabledIs();
        model.addAttribute("priceList", priceList);
        return "all-priceLists";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/admin"}, method = RequestMethod.GET)
    public String findAll(Model model) {
        List<PriceListDTO> priceList = priceListService.findAll();
        model.addAttribute("priceList", priceList);
        return "all-priceLists";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/addPriceList"}, method = RequestMethod.GET)
    public String showAddPriceListPage(Model model) {
        PriceListDTO priceList = new PriceListDTO();
        model.addAttribute("priceList", priceList);
        return "addPriceList";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/update"}, method = RequestMethod.GET)
    public String update(@RequestParam(name = "priceListId") Long priceListId, Model model) {
        PriceListDTO priceList = priceListService.findByIdDTO(priceListId);
        model.addAttribute("priceList", priceList);
        return "addPriceList";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/delete"}, method = RequestMethod.GET)
    public String delete(@RequestParam(name = "priceListId") Long priceListId) {
        priceListService.delete(priceListId);
        return "redirect:/api/priceLists/admin/";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/save"}, method = RequestMethod.POST)
    public String save(@ModelAttribute(name = "priceList") PriceListDTO priceList) {
        priceListService.save(priceList);
        return "redirect:/api/priceLists/admin/";
    }

////////////////////////////////////////////////

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @RequestMapping(value = {"/after-registration"}, method = RequestMethod.GET)
    public String afterRegistration(HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserDTO user = userService.findByLoginDTO(username);
        double balanceDiscount = discount.multiply(BigDecimal.valueOf(user.getBalance())).doubleValue();
        session.setAttribute("balanceDiscount", balanceDiscount);
        session.setAttribute("username", username);
        session.setAttribute("basket", basket.size());
        session.setAttribute("favorite", user.getFavorites().size());
        return "redirect:/api/priceLists/";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @RequestMapping(value = {"/before-logout"}, method = RequestMethod.GET)
    public String beforeLogout(HttpSession session) {
        session.invalidate();
        basket.clear();
        return "redirect:/api/priceLists/";
    }
}
