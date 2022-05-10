package by.university.hippo.service.interfaces;

import by.university.hippo.DTO.OrderDTO;
import by.university.hippo.DTO.ServiceDTO;
import by.university.hippo.entity.Order;
import by.university.hippo.service.IService;

import java.util.List;

public interface IOrderService extends IService<Order, Long, OrderDTO> {
    List<OrderDTO> findByUsername(String username);

    void updateStatus(Long orderId);

    void save(Order entity);

    void save(OrderDTO entity);

    List<ServiceDTO> beforeSave(String username);
}
