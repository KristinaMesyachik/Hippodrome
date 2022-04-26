package by.university.hippo.DTO;

import by.university.hippo.entity.Horse;
import by.university.hippo.entity.Staff;
import by.university.hippo.entity.enums.Place;

import java.time.LocalDateTime;
import java.util.List;

public class ServiceDTO {
    private Long id;
    private String title;
    private String description;
    private double cost;
    private Place place;
    private LocalDateTime time;
    private List<Horse> horses;
    private List<Staff> staff;
    private int enabled;
}
