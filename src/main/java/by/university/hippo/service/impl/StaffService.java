package by.university.hippo.service.impl;

import by.university.hippo.entity.Card;
import by.university.hippo.entity.Staff;
import by.university.hippo.exception.NoSuchHippoException;
import by.university.hippo.repository.IStaffRepository;
import by.university.hippo.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<Staff> staffOptional = staffRepository.findById(id);
        if (staffOptional.isEmpty()) {
            throw new NoSuchHippoException("There is no employee with ID = " + id + "in database");
        }
        return staffOptional.get();
    }



    @Override
    public void delete(Long id) {
        Optional<Staff> staffOptional = staffRepository.findById(id);
        if (staffOptional.isEmpty()) {
            throw new NoSuchHippoException("There is no employee with ID = " + id + "in database");
        } else if (staffOptional.get().getEnabled() == 1) {
            staffOptional.get().setEnabled(0);
        } else {
            staffOptional.get().setEnabled(1);
        }
        staffRepository.save(staffOptional.get());
    }

//    @Override
    public void save(Staff entity) {
        staffRepository.save(entity);
    }
}
