package by.university.hippo.service.impl;

import by.university.hippo.entity.Order;
import by.university.hippo.repository.IOrderRepository;
import by.university.hippo.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService implements IService<Order, Long> {

    @Autowired
    private IOrderRepository orderRepository;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void save(Order entity) {

    }
}
