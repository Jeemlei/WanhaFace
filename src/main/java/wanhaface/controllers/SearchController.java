package wanhaface.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Eemeli
 */
@Controller
public class SearchController {
    
    @GetMapping("/search")
    @ResponseBody
    public String search() {
        return "FEATURE UNDER CONSTRUCTION";
    }
}
