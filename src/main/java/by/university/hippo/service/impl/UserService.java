package by.university.hippo.service.impl;

import by.university.hippo.entity.User;
import by.university.hippo.repository.IUserRepository;
import by.university.hippo.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IService<User, Long> {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void save(User entity) {

    }
}
