package by.university.hippo.DTO;

import by.university.hippo.entity.enums.Place;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDTO {
    private Long id;
    private AboutServiceDTO aboutService;
    private String date;
    private String time;
    private Place place;
    private List<HorseDTO> horses;
    private List<StaffDTO> staff;
    private String enabled;
}
