package by.university.hippo.DTO;

import by.university.hippo.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HorseDTO {
    private Long id;
    private String nickname;
    private String breed;
    private Gender gender;
    private int age;
    private String description;
    private double rating;
    private String enabled;
}
