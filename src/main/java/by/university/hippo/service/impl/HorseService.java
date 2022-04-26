package by.university.hippo.service.impl;

import by.university.hippo.entity.Horse;
import by.university.hippo.exception.NoSuchHippoException;
import by.university.hippo.repository.IHorseRepository;
import by.university.hippo.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


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
        Optional<Horse> horseOptional = horseRepository.findById(id);
        if (horseOptional.isEmpty()) {
            throw new NoSuchHippoException("There is no horse with ID = " + id + "in database");
        }
        return horseOptional.get();
    }

    @Override
    public void delete(Long id) {
        Optional<Horse> horseOptional = horseRepository.findById(id);
        if (horseOptional.isEmpty()) {
            throw new NoSuchHippoException("There is no horse with ID = " + id + "in database");
        } else if (horseOptional.get().getEnabled() == 1) {
            horseOptional.get().setEnabled(0);
        } else {
            horseOptional.get().setEnabled(1);
        }
        horseRepository.save(horseOptional.get());
    }

//    @Override
    public void save(Horse entity) {
        horseRepository.save(entity);
    }
}
