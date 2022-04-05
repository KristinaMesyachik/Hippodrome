package by.university.hippo.service.impl;

import by.university.hippo.entity.InfoUser;
import by.university.hippo.repository.IInfoUserRepository;
import by.university.hippo.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InfoUserService implements IService<InfoUser, Long> {

    @Autowired
    private IInfoUserRepository infoUserRepository;

    @Override
    public List<InfoUser> findAll() {
        return infoUserRepository.findAll();
    }

    @Override
    public InfoUser findById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void save(InfoUser entity) {

    }
}
