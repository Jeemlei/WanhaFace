package wanhaface.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Eemeli
 */
@Controller
public class ProfileController {
    
    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }
}
