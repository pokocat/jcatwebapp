package com.ericsson.jcat.jcatwebapp.instances;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Secured("ROLE_USER")
class InstancesController {

    private InstancesRepository instanceRepository;

    @Autowired
    public InstancesController(InstancesRepository instanceRepository) {
        this.instanceRepository = instanceRepository;
        init();
    }

    private void init() {
        instanceRepository.save(new Instance("new instance 1", "This is a what's up message..."));
        instanceRepository.save(new Instance("new instance 2", "This is a how's going message..."));
    }

    @ModelAttribute("page")
    public String module() {
        return "instances";
    }

    @RequestMapping(value = "instance", method = RequestMethod.GET)
    public String messages(Model model) {
        model.addAttribute("instances", instanceRepository.findAll());
        return "instances/list-instances";
    }
}
