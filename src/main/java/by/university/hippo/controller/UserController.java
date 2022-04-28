package by.university.hippo.controller;

import by.university.hippo.DTO.UserAddDTO;
import by.university.hippo.entity.Service;
import by.university.hippo.entity.User;
import by.university.hippo.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String findAll(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "all-users";
    }

    @RequestMapping(value = {"/info"}, method = RequestMethod.GET)
    public String viewPeople(Model model, @RequestParam Long userId) {
        User user = userService.findById(userId);
        model.addAttribute("info", user.getInfoUser());
        model.addAttribute("user", user);
        return "about-user";
    }

    @RequestMapping(value = {"/updateRole"}, method = RequestMethod.GET)
    public String updateRole(@RequestParam(name = "userId") Long userId) {
        userService.updateRole(userId);
        return "redirect:/api/users/";
    }

    @RequestMapping(value = {"/updateBlock"}, method = RequestMethod.GET)
    public String updateBlock(@RequestParam(name = "userId") Long userId) {
        userService.updateBlock(userId);
        return "redirect:/api/users/";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(name = "userId") Long userId) {
        userService.delete(userId);
        return "redirect:/api/users/";
    }

    @RequestMapping(value = {"/addUser"}, method = RequestMethod.GET)
    public String addUserPage(Model model) {
        UserAddDTO user = new UserAddDTO();
        model.addAttribute("user", user);
        return "addUser";
    }

    @RequestMapping(value = {"/save"}, method = RequestMethod.POST)
    public String save(@ModelAttribute(name = "user") UserAddDTO user) {
            userService.save(user);
        return "successful-addUser";
    }

    @RequestMapping(value = {"/addFavorites"}, method = RequestMethod.GET)
    public String addFavoriteService(@RequestParam(name = "serviceId") Long serviceId, HttpSession session) {
        String username = (String) session.getAttribute("username");
        int countFavorite = userService.addFavorites(serviceId, username);
        session.setAttribute("favorite", countFavorite);
        return "redirect:/api/services/";
    }

    @RequestMapping(value = {"/deleteFavorites"}, method = RequestMethod.GET)
    public String delFavoriteService(@RequestParam(name = "serviceId") Long serviceId, HttpSession session) {
        String username = (String) session.getAttribute("username");
        int countFavorite = userService.delFavorites(serviceId, username);
        session.setAttribute("favorite", countFavorite);
        return "redirect:/api/users/favorites";
    }

    @RequestMapping(value = {"/favorites"}, method = RequestMethod.GET)
    public String viewFavoriteService(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        List<Service> favorites = userService.viewFavorites(username);
        model.addAttribute("favorites", favorites);
        return "favorites";
    }
}