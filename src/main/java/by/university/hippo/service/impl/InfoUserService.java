package by.university.hippo.service.impl;

import by.university.hippo.entity.InfoUser;
import by.university.hippo.exception.NoSuchHippoException;
import by.university.hippo.repository.IInfoUserRepository;
import by.university.hippo.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<InfoUser> infoUserOptional = infoUserRepository.findById(id);
        if (infoUserOptional.isEmpty()) {
            throw new NoSuchHippoException("There is no info with ID = " + id + "in database");
        }
        return infoUserOptional.get();
    }

    @Override
    public void delete(Long id) {

    }

//    @Override
    public void save(InfoUser entity) {
        infoUserRepository.save(entity);
    }
}
