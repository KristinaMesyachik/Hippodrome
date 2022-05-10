package by.university.hippo.service.interfaces;

import by.university.hippo.DTO.PriceListDTO;
import by.university.hippo.DTO.UserAddDTO;
import by.university.hippo.DTO.UserDTO;
import by.university.hippo.entity.User;
import by.university.hippo.service.IService;

import java.util.List;

public interface IUserService extends IService<User, Long, UserDTO> {

    User findByLogin(String login);

    UserDTO findByLoginDTO(String login);

    void save(UserAddDTO entity);

    void save(User entity);

    void updateRole(Long userId);

    int addFavorites(Long serviceId, String username);

    int delFavorites(Long serviceId, String username);

    List<PriceListDTO> viewFavorites(String username);

    void updateBlock(Long userId);
}
