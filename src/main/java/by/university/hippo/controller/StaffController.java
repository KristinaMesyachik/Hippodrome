package by.university.hippo.controller;

import by.university.hippo.DTO.StaffDTO;
import by.university.hippo.service.impl.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String findAll(Model model) {
        List<StaffDTO> staff = staffService.findAll();
        model.addAttribute("staff", staff);
        return "all-staff";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/addStaff"}, method = RequestMethod.GET)
    public String showAddStaffPage(Model model) {
        StaffDTO staff = new StaffDTO();
        model.addAttribute("staff", staff);
        return "addStaff";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/save"}, method = RequestMethod.POST)
    public String save(@ModelAttribute(name = "staff") StaffDTO staff) {
        staffService.save(staff);
        return "redirect:/api/staff/";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/update"}, method = RequestMethod.GET)
    public String update(@RequestParam(name = "staffId") Long staffId, Model model) {
        StaffDTO staff = staffService.findByIdDTO(staffId);
        model.addAttribute("staff", staff);
        return "addStaff";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/delete"}, method = RequestMethod.GET)
    public String delete(@RequestParam(name = "staffId") Long staffId) {
        staffService.delete(staffId);
        return "redirect:/api/staff/";
    }
}
