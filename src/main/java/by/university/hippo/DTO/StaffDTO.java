package by.university.hippo.DTO;

import by.university.hippo.entity.enums.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private String middlename;
    private String phone;
    private String mail;
    private Department department;
    private String enabled;
}
