package wanhaface.controllers;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wanhaface.data.Account;
import wanhaface.data.AccountRepository;

/**
 *
 * @author Eemeli
 */
@Controller
public class SearchController {
    
    @Autowired
    private AccountRepository accountRepository;
    
    @GetMapping("/search")
    public String searchField(Model model) {
        model.addAttribute("result", new ArrayList<Account>());
        return "search";
    }
    
    @GetMapping("/search/{string}")
    public String searchResult(Model model, @PathVariable String string) {
        model.addAttribute("result", accountRepository.findByUsernameLikeIgnoreCaseOrNameLikeIgnoreCase("%" + string + "%", "%" + string + "%"));
        return "search";
    }
    
    @PostMapping("/search")
    public String search(@RequestParam String string) {
        return "redirect:/search/" + string;
    }
}
