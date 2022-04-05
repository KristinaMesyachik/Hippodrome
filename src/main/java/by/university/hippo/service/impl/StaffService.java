package by.university.hippo.service.impl;

import by.university.hippo.entity.Staff;
import by.university.hippo.repository.IStaffRepository;
import by.university.hippo.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService implements IService<Staff, Long> {
    @Autowired
    private IStaffRepository staffRepository;

    @Override
    public List<Staff> findAll() {
        return staffRepository.findAll();
    }

    @Override
    public Staff findById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void save(Staff entity) {

    }
}
