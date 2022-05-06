package by.university.hippo.repository;

import by.university.hippo.entity.AboutService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAboutServiceRepository extends JpaRepository<AboutService, Long> {
}
