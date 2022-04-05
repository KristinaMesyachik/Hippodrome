package by.university.hippo.controller;

import by.university.hippo.entity.Service;
import by.university.hippo.service.impl.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceController {
    @Autowired
    private ServiceService serviceService;

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public List<Service> findAll(Model model) {
        List<Service> services = serviceService.findAll();
        return services;
    }
}
