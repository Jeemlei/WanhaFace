package wanhaface;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Eemeli
 */
@Controller
public class DefaultController {
    
    @GetMapping("*")
    public String handleDefault() {
        return "redirect:/home";
    }
   
    @GetMapping("/home")
    public String home(Model model) {
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return "redirect:/profile";
        }
        model.addAttribute("title", "WanhaFace");
        return "index";
    }
}
