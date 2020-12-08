package wanhaface.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Eemeli
 */
@Controller
public class ProfileController {
    
    @GetMapping("/profile")
    public String profile(Model model) {
        String username =  SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("title", "Profile: " + username);
        model.addAttribute("username", username);
        return "profile";
    }
}
