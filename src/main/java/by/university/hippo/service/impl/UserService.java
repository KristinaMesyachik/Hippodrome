package by.university.hippo.service.impl;

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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IService<User, Long>, UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

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
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new NoSuchHippoException("There is no user with ID = " + id + "in database");
        } else if (userOptional.get().getEnabled() == 1) {
            userOptional.get().setEnabled(0);
        } else {
            userOptional.get().setEnabled(1);
        }
    }

//        @Override
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
