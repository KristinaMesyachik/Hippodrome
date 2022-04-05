package by.university.hippo.controller;

import by.university.hippo.entity.InfoUser;
import by.university.hippo.service.impl.InfoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/info")
public class InfoUserController {
    @Autowired
    private InfoUserService infoUserService;

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public List<InfoUser> findAll(Model model) {
        List<InfoUser> infoUsers = infoUserService.findAll();
        return infoUsers;
    }
}
