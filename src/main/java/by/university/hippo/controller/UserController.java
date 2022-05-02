package by.university.hippo.controller;

import by.university.hippo.DTO.InfoUserDTO;
import by.university.hippo.DTO.ServiceDTO;
import by.university.hippo.DTO.UserAddDTO;
import by.university.hippo.DTO.UserDTO;
import by.university.hippo.service.impl.InfoUserService;
import by.university.hippo.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Autowired
    private InfoUserService infoUserService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String findAll(Model model) {
        List<UserDTO> users = userService.findAll();
        model.addAttribute("users", users);
        return "all-users";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/info"}, method = RequestMethod.GET)
    public String viewPeople(Model model, @RequestParam Long userId) {
        UserDTO user = userService.findById(userId);
        InfoUserDTO infoUserDTO = infoUserService.findById(user.getInfoUserId());
        model.addAttribute("info", infoUserDTO);
        model.addAttribute("user", user);
        return "about-user";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/updateRole"}, method = RequestMethod.GET)
    public String updateRole(@RequestParam(name = "userId") Long userId) {
        userService.updateRole(userId);
        return "redirect:/api/users/";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/updateBlock"}, method = RequestMethod.GET)
    public String updateBlock(@RequestParam(name = "userId") Long userId) {
        userService.updateBlock(userId);
        return "redirect:/api/users/";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
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

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/save"}, method = RequestMethod.POST)
    public String save(@ModelAttribute(name = "user") UserAddDTO user) {
            userService.save(user);
        return "successful-addUser";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/addFavorites"}, method = RequestMethod.GET)
    public String addFavoriteService(@RequestParam(name = "serviceId") Long serviceId, HttpSession session) {
        String username = (String) session.getAttribute("username");
        int countFavorite = userService.addFavorites(serviceId, username);
        session.setAttribute("favorite", countFavorite);
        return "redirect:/api/services/";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/deleteFavorites"}, method = RequestMethod.GET)
    public String delFavoriteService(@RequestParam(name = "serviceId") Long serviceId, HttpSession session) {
        String username = (String) session.getAttribute("username");
        int countFavorite = userService.delFavorites(serviceId, username);
        session.setAttribute("favorite", countFavorite);
        return "redirect:/api/users/favorites";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/favorites"}, method = RequestMethod.GET)
    public String viewFavoriteService(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        List<ServiceDTO> favorites = userService.viewFavorites(username);
        model.addAttribute("favorites", favorites);
        return "favorites";
    }
}