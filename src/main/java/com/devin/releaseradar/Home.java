package com.devin.releaseradar;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;



@Controller
public class Home {
    

    @GetMapping("/")
    public String getMethodName(Model model) {
        model.addAttribute("greeting", new Greeting());
        return "greeting";
    }
    
    @PostMapping("/")
    public String postMethodName(@ModelAttribute Greeting greeting, Model model) {
        //TODO: process POST request
        model.addAttribute("greeting", greeting);
        return "result";
    }
    

}
