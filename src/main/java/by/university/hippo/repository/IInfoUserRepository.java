package by.university.hippo.repository;

import by.university.hippo.entity.InfoUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IInfoUserRepository extends JpaRepository<InfoUser, Long> {
}
