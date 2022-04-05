package by.university.hippo.repository;

import by.university.hippo.entity.Horse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IHorseRepository extends JpaRepository<Horse, Long> {
}
