package by.university.hippo.DTO;

import by.university.hippo.entity.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AboutServiceDTO {
    private Long id;
    private String title;
    private String description;
    private double cost;
    private Category category;
    private String enabled;
}
