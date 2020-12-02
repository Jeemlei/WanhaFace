package wanhaface;

import org.springframework.stereotype.Controller;
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
    public String home() {
        return "index";
    }
}
