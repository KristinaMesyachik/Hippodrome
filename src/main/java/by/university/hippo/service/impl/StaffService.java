package by.university.hippo.service.impl;

import by.university.hippo.DTO.StaffDTO;
import by.university.hippo.entity.Staff;
import by.university.hippo.exception.NoSuchHippoException;
import by.university.hippo.repository.IStaffRepository;
import by.university.hippo.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StaffService implements IService<Staff, Long, StaffDTO> {
    @Autowired
    private IStaffRepository staffRepository;

    @Override
    public List<StaffDTO> findAll() {
        return staffRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StaffDTO findById(Long id) {
        Optional<Staff> staffOptional = staffRepository.findById(id);
        if (staffOptional.isEmpty()) {
            throw new NoSuchHippoException("There is no employee with ID = " + id + "in database");
        }
        return mapToDTO(staffOptional.get());
    }

    @Override
    public void delete(Long id) {
        StaffDTO staff = findById(id);
        if (Objects.equals(staff.getEnabled(), "Блокировано")) {
            staff.setEnabled("Активно");
        } else {
            staff.setEnabled("Блокировано");
        }
        save(staff);
    }

//    @Override
    public void save(StaffDTO entity) {
        staffRepository.save(mapToEntity(entity));
    }

    @Override
    public StaffDTO mapToDTO(Staff entity) {
        StaffDTO staffDTO = new StaffDTO();
        staffDTO.setId(entity.getId());
        staffDTO.setFirstname(entity.getFirstname());
        staffDTO.setLastname(entity.getLastname());
        staffDTO.setMiddlename(entity.getMiddlename());
        staffDTO.setPhone(entity.getPhone());
        staffDTO.setMail(entity.getMail());
        staffDTO.setDepartment(entity.getDepartment());
        if (entity.getEnabled() == 0) {
            staffDTO.setEnabled("Блокировано");
        } else {
            staffDTO.setEnabled("Активно");
        }
        return staffDTO;
    }

    @Override
    public Staff mapToEntity(StaffDTO dto) {
        Staff staff = new Staff();
        staff.setId(dto.getId());
        staff.setFirstname(dto.getFirstname());
        staff.setLastname(dto.getLastname());
        staff.setMiddlename(dto.getMiddlename());
        staff.setPhone(dto.getPhone());
        staff.setMail(dto.getMail());
        staff.setDepartment(dto.getDepartment());
        if (Objects.equals(dto.getEnabled(), "Блокировано")) {
            staff.setEnabled(0);
        } else {
            staff.setEnabled(1);
        }
        return staff;
    }
}