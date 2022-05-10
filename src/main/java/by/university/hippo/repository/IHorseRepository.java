package by.university.hippo.repository;

import by.university.hippo.entity.Horse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IHorseRepository extends JpaRepository<Horse, Long> {
    List<Horse> findByEnabledIs(int enabled);
}
