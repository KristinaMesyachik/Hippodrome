package by.university.hippo.controller;

import by.university.hippo.entity.User;
import by.university.hippo.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public List<User> findAll(Model model) {
        List<User> users = userService.findAll();
        return users;
    }
}