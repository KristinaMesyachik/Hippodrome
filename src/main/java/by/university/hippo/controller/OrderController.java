package by.university.hippo.controller;

import by.university.hippo.entity.Order;
import by.university.hippo.entity.User;
import by.university.hippo.service.impl.OrderService;
import by.university.hippo.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String findAllUser(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        List<Order> orders = orderService.findByUsername(username);
        model.addAttribute("orders",orders);
        return "all-orders";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/admin"}, method = RequestMethod.GET)
    public String findAll(Model model, HttpSession session) {
        List<Order> orders = orderService.findAll();
        model.addAttribute("orders",orders);
        return "all-orders";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/addOrder"}, method = RequestMethod.GET)
    public String addOrderPage(HttpSession session) {
        String username = (String) session.getAttribute("username");
        User user = userService.findByLogin(username);
        String str = orderService.beforeSave(ServiceController.basket, username);
        session.setAttribute("basket", ServiceController.basket.size());
        session.setAttribute("balance", user.getBalance());
        return str;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/updateStatus"}, method = RequestMethod.GET)
    public String updateBlock(@RequestParam(name = "orderId") Long orderId) {
        orderService.updateStatus(orderId);
        return "redirect:/api/orders/admin/";
    }
}
