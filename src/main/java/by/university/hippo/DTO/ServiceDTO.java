package by.university.hippo.DTO;

import by.university.hippo.entity.Horse;
import by.university.hippo.entity.Staff;
import by.university.hippo.entity.enums.Place;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDTO {
    private Long id;
    private String title;
    private String description;
    private double cost;
    private Place place;
    private String date;
    private String time;
//    private List<Horse> horses;
//    private List<Staff> staff;
    private String enabled;
}
