package by.university.hippo.repository;

import by.university.hippo.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IStaffRepository extends JpaRepository<Staff, Long> {
}
