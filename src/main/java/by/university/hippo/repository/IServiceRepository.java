package by.university.hippo.repository;

import by.university.hippo.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IServiceRepository extends JpaRepository<Service, Long> {
    List<Service> findByEnabledIs(int enabled);
}
