package by.university.hippo.controller;

import by.university.hippo.entity.Horse;
import by.university.hippo.service.impl.HorseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/horses")
public class HorseController {
    @Autowired
    private HorseService horseService;

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public List<Horse> findAll(Model model) {
        List<Horse> horses = horseService.findAll();
        return horses;
    }
}
