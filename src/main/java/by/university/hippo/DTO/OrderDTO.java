package by.university.hippo.DTO;

import by.university.hippo.entity.Service;
import by.university.hippo.entity.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
    private Long id;
    private Status status;
    private LocalDateTime time;
    private double amount;
    private List<Service> services;
    private long userId;
}
