package by.university.hippo.DTO;

import by.university.hippo.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String login;
    private Role role;
    private double balance;
    private String enabled;
    private Long infoUserId;
//    private InfoUserDTO infoUser;
//    private List<OrderDTO> orders;
    private List<ServiceDTO> favorites;
}
