package eu.balev.pushme.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	@RequestMapping("/")
    public String root() {
        return "index";
    }
	
	@RequestMapping("/home")
    public String home() {
        return "index";
    }
}
