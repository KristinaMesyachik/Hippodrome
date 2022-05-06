package by.university.hippo.service.impl;

import by.university.hippo.DTO.HorseDTO;
import by.university.hippo.entity.Horse;
import by.university.hippo.exception.NoSuchHippoException;
import by.university.hippo.repository.IHorseRepository;
import by.university.hippo.service.interfaces.IHorseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class HorseService implements IHorseService {

    @Autowired
    private IHorseRepository horseRepository;

    @Override
    public List<HorseDTO> findAll() {
        return horseRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
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
    public HorseDTO findByIdDTO(Long id) {
        return mapToDTO(findById(id));
    }

    @Override
    public void delete(Long id) {
        Horse horse = findById(id);
        if (horse.getEnabled() == 1) {
            horse.setEnabled(0);
        } else {
            horse.setEnabled(1);
        }
        horseRepository.save(horse);
    }

    @Override
    public void save(HorseDTO dto) {
        save(mapToEntity(dto));
    }

    @Override
    public void save(Horse entity) {
        horseRepository.save(entity);
    }

    @Override
    public HorseDTO mapToDTO(Horse entity) {
        HorseDTO horseDTO = new HorseDTO();
        horseDTO.setId(entity.getId());
        horseDTO.setNickname(entity.getNickname());
        horseDTO.setBreed(entity.getBreed());
        horseDTO.setGender(entity.getGender());
        horseDTO.setAge(entity.getAge());
        if (entity.getEnabled() == 0) {
            horseDTO.setEnabled("Блокировано");
        } else {
            horseDTO.setEnabled("Активно");
        }
        horseDTO.setRating(entity.getRating());
        return horseDTO;
    }

    @Override
    public Horse mapToEntity(HorseDTO dto) {
        Horse service = new Horse();
        service.setId(dto.getId());
        service.setNickname(dto.getNickname());
        service.setBreed(dto.getBreed());
        service.setGender(dto.getGender());
        service.setAge(dto.getAge());
        if (Objects.equals(dto.getEnabled(), "Блокировано")) {
            service.setEnabled(0);
        } else {
            service.setEnabled(1);
        }
        service.setRating(dto.getRating());
        return service;
    }

    @Override
    public List<HorseDTO> mapListToDTO(List<Horse> list) {
        return list.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Horse> mapListToEntity(List<HorseDTO> dto) {
        return dto.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }
}
