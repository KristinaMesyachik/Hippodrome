package by.university.hippo.DTO;

import by.university.hippo.entity.InfoUser;
import by.university.hippo.entity.Order;
import by.university.hippo.entity.Service;
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
//    private InfoUser infoUser;
//    private List<Order> orders;
    private List<Service> favorites;
}
