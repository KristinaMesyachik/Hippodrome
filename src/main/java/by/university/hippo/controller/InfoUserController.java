package by.university.hippo.controller;

import by.university.hippo.entity.InfoUser;
import by.university.hippo.service.impl.InfoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/api/info")
public class InfoUserController {
    @Autowired
    private InfoUserService infoUserService;

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String findAll(Model model) {
        List<InfoUser> infoUsers = infoUserService.findAll();
        model.addAttribute("infoUsers", infoUsers);
        return "all-infoUsers";
    }
}
