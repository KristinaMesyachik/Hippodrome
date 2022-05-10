package by.university.hippo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceListDTO {
    private Long id;
    private int countOfPeople;
    private double amount;
    private int duration;
    private String enabled;
    private AboutServiceDTO aboutService;
}
