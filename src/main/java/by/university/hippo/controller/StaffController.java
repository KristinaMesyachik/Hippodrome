package by.university.hippo.controller;

import by.university.hippo.entity.Staff;
import by.university.hippo.service.impl.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/api/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String findAll(Model model) {
        List<Staff> staff = staffService.findAll();
        model.addAttribute("staff", staff);
        return "all-staff";
    }

    @RequestMapping(value = {"/addStaff"}, method = RequestMethod.GET)
    public String showAddStaffPage(Model model) {
        Staff staff = new Staff();
        model.addAttribute("staff", staff);
        return "addStaff";
    }

    @RequestMapping(value = {"/save"}, method = RequestMethod.POST)
    public String save(@ModelAttribute(name = "staff") Staff staff) {
        staffService.save(staff);
        return "redirect:/api/staff/";
    }
    @RequestMapping(value = {"/update"}, method = RequestMethod.GET)
    public String update(@RequestParam(name = "staffId") Long staffId, Model model) {
        Staff staff = staffService.findById(staffId);
        model.addAttribute("staff", staff);
        return "addStaff";
    }

    @RequestMapping(value = {"/delete"}, method = RequestMethod.GET)
    public String delete(@RequestParam(name = "staffId") Long staffId) {
        staffService.delete(staffId);
        return "redirect:/api/staff/";
    }
}
