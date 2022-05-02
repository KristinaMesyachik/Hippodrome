package by.university.hippo.service.impl;

import by.university.hippo.DTO.ServiceDTO;
import by.university.hippo.DTO.UserAddDTO;
import by.university.hippo.DTO.UserDTO;
import by.university.hippo.entity.InfoUser;
import by.university.hippo.entity.MyUserDetails;
import by.university.hippo.entity.Service;
import by.university.hippo.entity.User;
import by.university.hippo.entity.enums.Role;
import by.university.hippo.exception.NoSuchHippoException;
import by.university.hippo.repository.IUserRepository;
import by.university.hippo.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class UserService implements IService<User, Long, UserDTO>, UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private InfoUserService infoUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ServiceService serviceService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByLoginUser(username);
        return new MyUserDetails(user);
    }

    public User findByLoginUser(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    public UserDTO findByLogin(String login) {
        return mapToDTO(userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("user not found")));
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findById(Long id) {
        return mapToDTO(findByIdUser(id));
    }

    public User findByIdUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new NoSuchHippoException("There is no user with ID = " + id + "in database");
        }
        return userOptional.get();
    }

    @Override
    public void delete(Long id) {
        Long userTemp = findByIdUser(id).getInfoUser().getId();
        userRepository.deleteById(findById(id).getId());
        infoUserService.delete(userTemp);
    }

    //        @Override
    public void save(UserAddDTO entity) {
        User user = new User();
        InfoUser infoUser = new InfoUser();

        infoUser.setFirstname(entity.getFirstname());
        infoUser.setLastname(entity.getFirstname());
        infoUser.setMiddlename(entity.getMiddlename());
        infoUser.setMail(entity.getMail());
        infoUser.setPhone(entity.getPhone());
        infoUserService.save(infoUser);

        user.setLogin(entity.getLogin());
        user.setPassword(passwordEncoder.encode(entity.getPassword()));
        user.setRole(Role.USER);
        user.setBalance(0);
        user.setEnabled(1);
        user.setInfoUser(infoUser);
        userRepository.save(user);
    }

    public void save(User entity) {
        userRepository.save(entity);
    }

    public void updateRole(Long userId) {
        User user = findByIdUser(userId);
        if (user.getRole().equals(Role.USER)) {
            user.setRole(Role.ADMIN);
        } else {
            user.setRole(Role.USER);
        }
        save(user);
    }

    public int addFavorites(Long serviceId, String username) {
        User user = findByLoginUser(username);
        List<Service> serviceList = user.getFavorites();
        Service service = serviceService.mapToEntity(serviceService.findById(serviceId));
        serviceList.add(service);
        user.setFavorites(serviceList);
        userRepository.save(user);
        return user.getFavorites().size();
    }

    public int delFavorites(Long serviceId, String username) {
        User user = findByLoginUser(username);
        List<Service> serviceList = user.getFavorites();
        serviceList.remove(serviceService.mapToEntity(serviceService.findById(serviceId)));
        user.setFavorites(serviceList);
        userRepository.save(user);
        return user.getFavorites().size();
    }

    public List<ServiceDTO> viewFavorites(String username) {
        User user = findByLoginUser(username);
        List<ServiceDTO> serviceDTOS = new ArrayList<>();
        for (Service service : user.getFavorites()) {
            serviceDTOS.add(serviceService.mapToDTO(service));
        }
        return serviceDTOS;
    }

    public void updateBlock(Long userId) {
        User user = findByIdUser(userId);
        if (user.getEnabled() == 0) {
            user.setEnabled(1);
        } else {
            user.setEnabled(0);
        }
        save(user);
    }

    @Override
    public UserDTO mapToDTO(User entity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(entity.getId());
        userDTO.setLogin(entity.getLogin());
        userDTO.setRole(entity.getRole());
        userDTO.setBalance(entity.getBalance());
        userDTO.setInfoUserId(entity.getInfoUser().getId());
//        userDTO.setOrders(entity.getOrders());
        userDTO.setFavorites(entity.getFavorites());
        if (entity.getEnabled() == 0) {
            userDTO.setEnabled("Блокировано");
        } else {
            userDTO.setEnabled("Активно");
        }
        return userDTO;
    }

    @Override
    public User mapToEntity(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setLogin(dto.getLogin());
        user.setRole(dto.getRole());
        user.setBalance(dto.getBalance());
        user.setInfoUser(infoUserService.findByIdInfo(dto.getInfoUserId()));
//        user.setOrders(dto.getOrders());
        user.setFavorites(dto.getFavorites());
        if (Objects.equals(dto.getEnabled(), "Блокировано")) {
            user.setEnabled(0);
        } else {
            user.setEnabled(1);
        }
        return user;
    }
}
