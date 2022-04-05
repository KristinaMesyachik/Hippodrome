package by.university.hippo.service.impl;

import by.university.hippo.entity.Horse;
import by.university.hippo.repository.IHorseRepository;
import by.university.hippo.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class HorseService implements IService<Horse, Long> {

    @Autowired
    private IHorseRepository horseRepository;

    @Override
    public List<Horse> findAll() {
        return horseRepository.findAll();
    }

    @Override
    public Horse findById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void save(Horse entity) {

    }
}
