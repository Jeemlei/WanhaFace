package wanhaface;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated()) {
            return "redirect:/profile";
        }
        model.addAttribute("title", "WanhaFace");
        return "index";
    }
}
