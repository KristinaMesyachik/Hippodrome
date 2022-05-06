package by.university.hippo.service.interfaces;

import by.university.hippo.DTO.StaffDTO;
import by.university.hippo.entity.Staff;
import by.university.hippo.service.IService;

public interface IStaffService extends IService<Staff, Long, StaffDTO> {
    void save(Staff entity);

    void save(StaffDTO entity);
}
