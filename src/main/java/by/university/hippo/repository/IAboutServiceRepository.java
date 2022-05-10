package by.university.hippo.repository;

import by.university.hippo.entity.AboutService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IAboutServiceRepository extends JpaRepository<AboutService, Long> {
    List<AboutService> findByEnabledIs(int enabled);
}
