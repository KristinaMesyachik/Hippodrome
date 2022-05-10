package by.university.hippo.service.impl;

import by.university.hippo.DTO.PriceListDTO;
import by.university.hippo.DTO.UserAddDTO;
import by.university.hippo.DTO.UserDTO;
import by.university.hippo.entity.*;
import by.university.hippo.entity.enums.Role;
import by.university.hippo.exception.NoSuchHippoException;
import by.university.hippo.repository.IUserRepository;
import by.university.hippo.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class UserService implements IUserService, UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private InfoUserService infoUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PriceListService priceListService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByLogin(username);
        return new MyUserDetails(user);
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    @Override
    public UserDTO findByLoginDTO(String login) {
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
    public UserDTO findByIdDTO(Long id) {
        return mapToDTO(findById(id));
    }

    @Override
    public User findById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new NoSuchHippoException("There is no user with ID = " + id + "in database");
        }
        return userOptional.get();
    }

    @Override
    public void delete(Long id) {
        Long userTemp = findById(id).getInfoUser().getId();
        userRepository.deleteById(findById(id).getId());
        infoUserService.delete(userTemp);
    }

    @Override
    public void save(UserAddDTO entity) {
        User user = new User();
        InfoUser infoUser = new InfoUser();

        infoUser.setFirstname(entity.getFirstname());
        infoUser.setLastname(entity.getFirstname());
        infoUser.setMiddlename(entity.getMiddlename());
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

    @Override
    public void save(User entity) {
        userRepository.save(entity);
    }

    @Override
    public void updateRole(Long userId) {
        User user = findById(userId);
        if (user.getRole().equals(Role.USER)) {
            user.setRole(Role.ADMIN);
        } else {
            user.setRole(Role.USER);
        }
        save(user);
    }

    @Override
    public int addFavorites(Long serviceId, String username) {
        User user = findByLogin(username);
        List<PriceList> serviceList = user.getFavorites();
        PriceList priceList = priceListService.findById(serviceId);
        serviceList.add(priceList);
        user.setFavorites(serviceList);
        userRepository.save(user);
        return user.getFavorites().size();
    }

    @Override
    public int delFavorites(Long serviceId, String username) {
        User user = findByLogin(username);
        List<PriceList> priceListList = user.getFavorites();
        priceListList.remove(priceListService.findById(serviceId));
        user.setFavorites(priceListList);
        userRepository.save(user);
        return user.getFavorites().size();
    }

    @Override
    public List<PriceListDTO> viewFavorites(String username) {
        User user = findByLogin(username);
        return priceListService.mapListToDTO(user.getFavorites());
    }

    @Override
    public void updateBlock(Long userId) {
        User user = findById(userId);
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
        userDTO.setInfoUser(infoUserService.mapToDTO(entity.getInfoUser()));
//        userDTO.setOrders(entity.getOrders());
        userDTO.setFavorites(priceListService.mapListToDTO(entity.getFavorites()));
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
        user.setInfoUser(infoUserService.mapToEntity(dto.getInfoUser()));
//        user.setOrders(dto.getOrders());
        user.setFavorites(priceListService.mapListToEntity(dto.getFavorites()));
        if (Objects.equals(dto.getEnabled(), "Блокировано")) {
            user.setEnabled(0);
        } else {
            user.setEnabled(1);
        }
        return user;
    }

    @Override
    public List<UserDTO> mapListToDTO(List<User> list) {
        return list.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> mapListToEntity(List<UserDTO> dto) {
        return dto.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }
}
