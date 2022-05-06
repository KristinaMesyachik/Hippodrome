package by.university.hippo.DTO;

import by.university.hippo.entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private Status status;
    private LocalDateTime time;
    private double amount;
    private double amountWithSale;
    private List<ServiceDTO> services;
    private long userId;
}
