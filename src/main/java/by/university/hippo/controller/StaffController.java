package by.university.hippo.controller;

import by.university.hippo.entity.Staff;
import by.university.hippo.service.impl.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public List<Staff> findAll(Model model) {
        List<Staff> staffs = staffService.findAll();
        return staffs;
    }
}
