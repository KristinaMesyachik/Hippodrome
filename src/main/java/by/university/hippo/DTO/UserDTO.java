package by.university.hippo.DTO;

import by.university.hippo.entity.InfoUser;
import by.university.hippo.entity.Order;
import by.university.hippo.entity.enums.Role;

import java.util.List;

public class UserDTO {
    private Long id;
    private String login;
    private String password;
    private Role role;
    private double balance;
    private int enabled;
    private InfoUser infoUser;
    private List<Order> orders;
}
