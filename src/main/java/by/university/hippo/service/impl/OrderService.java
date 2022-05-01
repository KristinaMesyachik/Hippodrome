package by.university.hippo.service.impl;

import by.university.hippo.controller.ServiceController;
import by.university.hippo.entity.Order;
import by.university.hippo.entity.Service;
import by.university.hippo.entity.User;
import by.university.hippo.entity.enums.Role;
import by.university.hippo.entity.enums.Status;
import by.university.hippo.exception.NoSuchHippoException;
import by.university.hippo.repository.IOrderRepository;
import by.university.hippo.service.IService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.university.hippo.entity.enums.Status.READY;

@org.springframework.stereotype.Service
public class OrderService implements IService<Order, Long> {

    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private UserService userService;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isEmpty()) {
            throw new NoSuchHippoException("There is no order with ID = " + id + "in database");
        }
        return orderOptional.get();
    }

    public List<Order> findByUsername(String username) {
        return orderRepository.findByUserId(userService.findByLogin(username).getId());
    }

    @Override
    public void delete(Long id) {

    }

    public void updateStatus(Long orderId) {
        Order order = findById(orderId);
        if (order.getStatus().equals(Status.IN_PROGRESS)) {
            order.setStatus(Status.READY);
        } else {
            order.setStatus(Status.IN_PROGRESS);
        }
        save(order);
    }
    //    @Override
    public void save(Order entity) {
        orderRepository.save(entity);
    }

    public String beforeSave(List<Long> basket, String username) {
        User user = userService.findByLogin(username);
        Order order = new Order();
        List<Service> services = new ArrayList<>();
        double cost = 0;
        for (Long i : basket) {
            Service service = serviceService.findById(i);
            cost += service.getCost();
            services.add(service);
        }
        if (user.getBalance() >= cost) {
            order.setServices(services);
            order.setTime(LocalDateTime.now());
            order.setStatus(Status.IN_PROGRESS);
            order.setAmount(cost);
            order.setUserId(user.getId());
            save(order);
            user.setBalance(user.getBalance() - cost);
            userService.save(user);
            ServiceController.basket.clear();
        } else {
            return "not-enough-money";
        }
        return "redirect:/api/orders/";
    }
}
