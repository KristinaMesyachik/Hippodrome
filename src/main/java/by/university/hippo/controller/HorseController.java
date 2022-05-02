package by.university.hippo.controller;

import by.university.hippo.DTO.HorseDTO;
import by.university.hippo.service.impl.HorseService;
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
@RequestMapping("/api/horses")
public class HorseController {

    @Autowired
    private HorseService horseService;

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String findAll(Model model) {
        List<HorseDTO> horses = horseService.findAll();
        model.addAttribute("horses",horses);
        return "all-horses";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/addHorse"}, method = RequestMethod.GET)
    public String showAddHorsePage(Model model) {
        HorseDTO horse = new HorseDTO();
        model.addAttribute("horse", horse);
        return "addHorse";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/save"}, method = RequestMethod.POST)
    public String save(@ModelAttribute(name = "horse") HorseDTO horse) {
        horseService.save(horse);
        return "redirect:/api/horses/";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/update"}, method = RequestMethod.GET)
    public String update(@RequestParam(name = "horseId") Long horseId, Model model) {
        HorseDTO horse = horseService.findById(horseId);
        model.addAttribute("horse", horse);
        return "addHorse";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/delete"}, method = RequestMethod.GET)
    public String delete(@RequestParam(name = "horseId") Long horseId) {
        horseService.delete(horseId);
        return "redirect:/api/horses/";
    }
}
