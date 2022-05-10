package by.university.hippo.controller;

import by.university.hippo.DTO.PriceListDTO;
import by.university.hippo.DTO.ServiceDTO;
import by.university.hippo.service.impl.PriceListService;
import by.university.hippo.service.impl.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/api/services")
@SessionAttributes(types = ServiceDTO.class)
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private PriceListService priceListService;

    public static HashMap<Integer, ServiceDTO> basket = new HashMap<>();

    public static BigDecimal discount = BigDecimal.valueOf(0.01);

    public static List<String> timeList = List.of(
            "10:00", "11:00", "12:00", "13:00", "14:00",
            "15:00", "16:00", "17:00", "18:00", "19:00", "20:00");

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
        model.addAttribute("services", services);
        return "all-services";
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

//////////////////////////////////////////////////////

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/beforeAddBasket"}, method = RequestMethod.GET)
    public String beforeAddBasket(@RequestParam(name = "priceListId") Long priceListId, HttpSession session, Model model) {
        PriceListDTO priceListDTO = priceListService.findByIdDTO(priceListId);
        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setPriceList(priceListDTO);
        model.addAttribute("service", serviceDTO);
        session.setAttribute("timeList", timeList);
        return "date_time";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/addBasket"}, method = RequestMethod.POST)
    public String addBasket(@ModelAttribute(name = "service") ServiceDTO service, HttpSession session) {
        service.setId((long) service.hashCode());
        basket.put(service.hashCode(), service);
        session.setAttribute("basket", basket.size());
        return "redirect:/api/priceLists/";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/basket"}, method = RequestMethod.GET)
    public String basket(Model model) {
        model.addAttribute("services", new ArrayList<>(basket.values()));
        return "basket";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/deleteFromBasket"}, method = RequestMethod.GET)
    public String deleteFromBasket(@RequestParam(name = "serviceId") Integer serviceId, HttpSession session) {
        basket.remove(serviceId);
        session.setAttribute("basket", basket.size());
        return "redirect:/api/services/basket";
    }
}
