package by.university.hippo.service.impl;

import by.university.hippo.DTO.UserAddDTO;
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

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class UserService implements IService<User, Long>, UserDetailsService {

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
        User user = findByLogin(username);
        return new MyUserDetails(user);
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
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
        Long ddd = findById(id).getInfoUser().getId();
        userRepository.delete(findById(id));
        infoUserService.delete(ddd);
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
        user.setBalance(20);
        user.setEnabled(1);
        user.setInfoUser(infoUser);
        userRepository.save(user);
    }

    public void save(User entity) {
        userRepository.save(entity);
    }

    public void updateRole(Long userId) {
        User user = findById(userId);
        if (user.getRole().equals(Role.USER)) {
            user.setRole(Role.ADMIN);
        } else {
            user.setRole(Role.USER);
        }
        save(user);
    }

    public int addFavorites(Long serviceId, String username) {
        User user = findByLogin(username);
        List<Service> serviceList = user.getFavorites();
        Service service = serviceService.findById(serviceId);
        serviceList.add(service);
        user.setFavorites(serviceList);
        save(user);
        return user.getFavorites().size();
    }

    public int delFavorites(Long serviceId, String username) {
        User user = findByLogin(username);
        List<Service> serviceList = user.getFavorites();
        serviceList.remove(serviceService.findById(serviceId));
        user.setFavorites(serviceList);
        save(user);
        return user.getFavorites().size();
    }

    public List<Service> viewFavorites(String username) {
        User user = findByLogin(username);
        return user.getFavorites();
    }

    public void updateBlock(Long userId) {
        User user = findById(userId);
        if (user.getEnabled() == 1) {
            user.setEnabled(0);
        } else {
            user.setEnabled(1);
        }
        save(user);
    }
}
