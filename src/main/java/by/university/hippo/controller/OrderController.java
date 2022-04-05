package by.university.hippo.controller;

import by.university.hippo.entity.Order;
import by.university.hippo.service.impl.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public List<Order> findAll(Model model) {
        List<Order> orders = orderService.findAll();
        return orders;
    }
}
