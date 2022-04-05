package by.university.hippo.repository;

import by.university.hippo.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IServiceRepository extends JpaRepository<Service, Long> {
}
