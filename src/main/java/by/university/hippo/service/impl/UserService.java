package by.university.hippo.service.impl;

import by.university.hippo.DTO.UserAddDTO;
import by.university.hippo.entity.InfoUser;
import by.university.hippo.entity.MyUserDetails;
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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IService<User, Long>, UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private InfoUserService infoUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
       userRepository.delete(findById(id));
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
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new NoSuchHippoException("There is no user with ID = " + userId + "in database");
        } else {
            User user = userOptional.get();
            if (user.getRole().equals(Role.USER)) {
                user.setRole(Role.ADMIN);
            } else {
                user.setRole(Role.USER);
            }
            save(user);
        }
    }

    public void updateBlock(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new NoSuchHippoException("There is no user with ID = " + userId + "in database");
        } else {
            User user = userOptional.get();
            if (user.getEnabled() == 1) {
                user.setEnabled(0);
            } else {
                user.setEnabled(1);
            }
            save(user);
        }
    }
}
