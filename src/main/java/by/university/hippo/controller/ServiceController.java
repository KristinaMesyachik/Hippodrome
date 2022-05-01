package by.university.hippo.controller;

import by.university.hippo.entity.Service;
import by.university.hippo.entity.User;
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/admin"}, method = RequestMethod.GET)
    public String findAll(Model model) {
        List<Service> services = serviceService.findAll();
        model.addAttribute("services", services);
        return "all-services";
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String findAllUser(Model model) {
        List<Service> services = serviceService.findByEnabledIs();
        model.addAttribute("services", services);
        return "all-services";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @RequestMapping(value = {"/after-registration"}, method = RequestMethod.GET)
    public String afterRegistration(HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findByLogin(username);
        session.setAttribute("balance", user.getBalance());
        session.setAttribute("username", username);
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
        Service service = new Service();
        model.addAttribute("service", service);
        return "addService";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/update"}, method = RequestMethod.GET)
    public String update(@RequestParam(name = "serviceId") Long serviceId, Model model) {
        Service service = serviceService.findById(serviceId);
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
    public String save(@ModelAttribute(name = "service") Service service) {
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
        List<Service> services = serviceService.viewBasket(basket);
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
