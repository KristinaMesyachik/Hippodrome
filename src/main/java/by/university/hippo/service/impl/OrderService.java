package by.university.hippo.service.impl;

import by.university.hippo.DTO.OrderDTO;
import by.university.hippo.DTO.ServiceDTO;
import by.university.hippo.entity.*;
import by.university.hippo.entity.enums.Status;
import by.university.hippo.exception.NoSuchHippoException;
import by.university.hippo.repository.IOrderRepository;
import by.university.hippo.service.interfaces.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static by.university.hippo.controller.ServiceController.basket;
import static by.university.hippo.controller.ServiceController.discount;


@org.springframework.stereotype.Service
public class OrderService implements IOrderService {

    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private UserService userService;

    @Override
    public List<OrderDTO> findAll() {
        return orderRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO findByIdDTO(Long id) {
        return mapToDTO(findById(id));
    }

    @Override
    public Order findById(Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isEmpty()) {
            throw new NoSuchHippoException("There is no order with ID = " + id + "in database");
        }
        return orderOptional.get();
    }

    @Override
    public List<OrderDTO> findByUsername(String username) {
        return mapListToDTO(orderRepository.findByUserId(userService.findByLoginDTO(username).getId()));
    }

    @Override
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public void updateStatus(Long orderId) {
        Order order = findById(orderId);
        if (order.getStatus().equals(Status.IN_PROGRESS)) {
            order.setStatus(Status.READY);
        } else {
            order.setStatus(Status.IN_PROGRESS);
        }
        save(order);
    }

    @Override
    public void save(Order entity) {
        orderRepository.save(entity);
    }

    @Override
    public void save(OrderDTO entity) {
        save(mapToEntity(entity));
    }

    @Override
    public List<ServiceDTO> beforeSave(String username) {
        List<Service> wrongService = serviceService.save(basket);
        if (wrongService.isEmpty()) {
            User user = userService.findByLogin(username);
            double balanceDiscount = BigDecimal.valueOf(100)
                    .subtract(discount.multiply(BigDecimal.valueOf(user.getBalance())))
                    .divide(BigDecimal.valueOf(100))
                    .doubleValue();
            OrderDTO order = new OrderDTO();
            double cost = 0;

            List<ServiceDTO> byArgsList = serviceService.findByArgsList(basket);

            for (ServiceDTO service : byArgsList) {
                cost += service.getPriceList().getAmount();
            }
            order.setServices(byArgsList);
            order.setTime(LocalDateTime.now());
            order.setStatus(Status.IN_PROGRESS);
            order.setAmount(cost);
            order.setAmountWithSale(cost * balanceDiscount);
            order.setUserId(user.getId());
            save(order);
            user.setBalance(user.getBalance() + cost);
            userService.save(user);
            basket.clear();
            return null;
        } else {
            return serviceService.mapListToDTO(wrongService);
        }
    }

    @Override
    public OrderDTO mapToDTO(Order entity) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(entity.getId());
        orderDTO.setStatus(entity.getStatus());
        orderDTO.setTime(entity.getTime());
        orderDTO.setAmount(entity.getAmount());
        orderDTO.setAmountWithSale(entity.getAmountWithSale());
        orderDTO.setServices(serviceService.mapListToDTO(entity.getServices()));
        orderDTO.setUserId(entity.getUserId());
        return orderDTO;
    }

    @Override
    public Order mapToEntity(OrderDTO dto) {
        Order order = new Order();
        order.setId(dto.getId());
        order.setStatus(dto.getStatus());
        order.setTime(dto.getTime());
        order.setAmount(dto.getAmount());
        order.setAmountWithSale(dto.getAmountWithSale());
        order.setServices(serviceService.mapListToEntity(dto.getServices()));
        order.setUserId(dto.getUserId());
        return order;
    }

    @Override
    public List<OrderDTO> mapListToDTO(List<Order> list) {
        return list.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> mapListToEntity(List<OrderDTO> dto) {
        return dto.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }
}
