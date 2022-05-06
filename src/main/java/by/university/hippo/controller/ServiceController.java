package by.university.hippo.controller;

import by.university.hippo.DTO.InfoUserDTO;
import by.university.hippo.DTO.ServiceDTO;
import by.university.hippo.DTO.UserDTO;
import by.university.hippo.service.impl.ServiceService;
import by.university.hippo.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/services")
public class ServiceController {
    @Autowired
    private ServiceService serviceService;

    @Autowired
    private UserService userService;

    public static List<Long> basket = new ArrayList<>();

    public static BigDecimal discount = BigDecimal.valueOf(0.01);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/admin"}, method = RequestMethod.GET)
    public String findAll(Model model) {
        List<ServiceDTO> services = serviceService.findAll();
        model.addAttribute("services", services);
        return "all-services";
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String findAllUser(Model model) {
        List<ServiceDTO> services = serviceService.findByEnabledIs();
        System.err.println(services);
        model.addAttribute("services", services);
        return "all-services";
    }

//    @PreAuthorize("hasRole('ROLE')")
    @RequestMapping(value = {"/about"}, method = RequestMethod.GET)
    public String viewAllService(Model model, @RequestParam Long serviceId) {
        ServiceDTO serviceDTO = serviceService.findByIdDTO(serviceId);
        model.addAttribute("service", serviceDTO);
        return "about-user";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @RequestMapping(value = {"/after-registration"}, method = RequestMethod.GET)
    public String afterRegistration(HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserDTO user = userService.findByLoginDTO(username);
        double balanceDiscount = discount.multiply(BigDecimal.valueOf(user.getBalance())).doubleValue();
        session.setAttribute("balanceDiscount", balanceDiscount);
        session.setAttribute("username", username);
        System.err.println(user);
        System.err.println(userService.findByLogin(username));
        session.setAttribute("basket", basket.size());
        session.setAttribute("favorite", user.getFavorites().size());
        return "redirect:/api/services/";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @RequestMapping(value = {"/before-logout"}, method = RequestMethod.GET)
    public String beforeLogout(HttpSession session) {
        session.invalidate();
        basket.clear();
        return "redirect:/api/services/";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/addService"}, method = RequestMethod.GET)
    public String showAddServicePage(Model model) {
        ServiceDTO service = new ServiceDTO();
        model.addAttribute("service", service);
        return "addService";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/update"}, method = RequestMethod.GET)
    public String update(@RequestParam(name = "serviceId") Long serviceId, Model model) {
        ServiceDTO service = serviceService.findByIdDTO(serviceId);
        model.addAttribute("service", service);
        return "addService";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/delete"}, method = RequestMethod.GET)
    public String delete(@RequestParam(name = "serviceId") Long serviceId) {
        serviceService.delete(serviceId);
        return "redirect:/api/services/admin/";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/save"}, method = RequestMethod.POST)
    public String save(@ModelAttribute(name = "service") ServiceDTO service) {
        serviceService.save(service);
        return "redirect:/api/services/admin/";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/addBasket"}, method = RequestMethod.GET)
    public String addBasket(@RequestParam(name = "serviceId") Long serviceId, HttpSession session) {
        basket = serviceService.addFromBasket(serviceId, basket);
        session.setAttribute("basket", basket.size());
        return "redirect:/api/services/";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/basket"}, method = RequestMethod.GET)
    public String basket(Model model) {
        List<ServiceDTO> services = serviceService.viewBasket(basket);
        model.addAttribute("services", services);
        return "basket";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/deleteFromBasket"}, method = RequestMethod.GET)
    public String deleteFromBasket(@RequestParam(name = "serviceId") Long serviceId, HttpSession session) {
        basket = serviceService.deleteFromBasket(serviceId, basket);
        session.setAttribute("basket", basket.size());
        return "redirect:/api/services/basket";
    }
}
