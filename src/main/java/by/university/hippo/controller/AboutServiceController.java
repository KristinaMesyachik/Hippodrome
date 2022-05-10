package by.university.hippo.controller;


import by.university.hippo.DTO.AboutServiceDTO;
import by.university.hippo.service.impl.AboutServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/api/about-services")
public class AboutServiceController {

    @Autowired
    private AboutServiceService aboutServiceService;

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String findAllUser(Model model) {
        List<AboutServiceDTO> aboutServices = aboutServiceService.findByEnabledIs();
        model.addAttribute("aboutServices", aboutServices);
        return "all-about-services";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/admin"}, method = RequestMethod.GET)
    public String findAll(Model model) {
        List<AboutServiceDTO> aboutServices = aboutServiceService.findAll();
        model.addAttribute("aboutServices", aboutServices);
        return "all-about-services";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/addAboutService"}, method = RequestMethod.GET)
    public String showAddServicePage(Model model) {
        AboutServiceDTO aboutService = new AboutServiceDTO();
        model.addAttribute("aboutService", aboutService);
        return "addAboutService";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/update"}, method = RequestMethod.GET)
    public String update(@RequestParam(name = "aboutServiceId") Long aboutServiceId, Model model) {
        AboutServiceDTO aboutService = aboutServiceService.findByIdDTO(aboutServiceId);
        model.addAttribute("aboutService", aboutService);
        return "addAboutService";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/delete"}, method = RequestMethod.GET)
    public String delete(@RequestParam(name = "aboutServiceId") Long aboutServiceId) {
        aboutServiceService.delete(aboutServiceId);
        return "redirect:/api/about-services/admin/";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/save"}, method = RequestMethod.POST)
    public String save(@ModelAttribute(name = "aboutService") AboutServiceDTO aboutServiceDTO) {
        aboutServiceService.save(aboutServiceDTO);
        return "redirect:/api/about-services/admin/";
    }
}
