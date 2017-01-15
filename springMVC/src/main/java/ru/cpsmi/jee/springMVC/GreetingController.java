package ru.cpsmi.jee.springMVC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {
    private final GreetingModel greetingModel;

    @Autowired
    public GreetingController(GreetingModel greetingModel) {
        this.greetingModel = greetingModel;
    }


    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="default") String name, Model model) {
        model.addAttribute("name", greetingModel.getGreeting(name));
        return "greeting";
    }
}
