package by.university.hippo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfoUserDTO {
    private Long id;
    private String lastname;
    private String firstname;
    private String middlename;
    private String phone;
    private String mail;
}
