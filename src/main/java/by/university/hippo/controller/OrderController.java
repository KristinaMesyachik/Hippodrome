package by.university.hippo.controller;

import by.university.hippo.DTO.OrderDTO;
import by.university.hippo.DTO.UserDTO;
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
import java.math.BigDecimal;
import java.util.List;

import static by.university.hippo.controller.ServiceController.discount;


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
        List<OrderDTO> orders = orderService.findByUsername(username);
        model.addAttribute("orders", orders);
        return "all-orders";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/admin"}, method = RequestMethod.GET)
    public String findAll(Model model, HttpSession session) {
        List<OrderDTO> orders = orderService.findAll();
        model.addAttribute("orders", orders);
        return "all-orders";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/addOrder"}, method = RequestMethod.GET)
    public String addOrderPage(HttpSession session) {
        String username = (String) session.getAttribute("username");
        UserDTO user = userService.findByLoginDTO(username);
        orderService.beforeSave(ServiceController.basket, username);
        session.setAttribute("basket", ServiceController.basket.size());
        double balanceDiscount = discount.multiply(BigDecimal.valueOf(user.getBalance())).doubleValue();
        session.setAttribute("balanceDiscount", balanceDiscount);
        return "redirect:/api/orders/updateDiscount";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/print"}, method = RequestMethod.GET)
    public String printOrder(HttpSession session) {//TODO
        return "redirect:/api/orders";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/sendMail"}, method = RequestMethod.GET)
    public String sendMailOrder(HttpSession session) {//TODO
        return "redirect:/api/orders";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/updateDiscount"}, method = RequestMethod.GET)
    public String updateDiscount(HttpSession session) {
        String username = (String) session.getAttribute("username");
        UserDTO user = userService.findByLoginDTO(username);
        double balanceDiscount = discount.multiply(BigDecimal.valueOf(user.getBalance())).doubleValue();
        session.setAttribute("balanceDiscount", balanceDiscount);
        return "redirect:/api/orders/";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/updateStatus"}, method = RequestMethod.GET)
    public String updateBlock(@RequestParam(name = "orderId") Long orderId) {
        orderService.updateStatus(orderId);
        return "redirect:/api/orders/admin/";
    }
}
