package wanhaface.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import wanhaface.data.Account;
import wanhaface.data.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;

/**
 *
 * @author Eemeli
 */
@Controller
public class RegistrationController {

    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String registrationPage(Model model) {
        model.addAttribute("title", "Register");
        return "register";
    }

    @PostMapping("/register")
    public String registerNewAccount(@RequestParam String name,
                                    @RequestParam String username,
                                    @RequestParam String password,
                                    @RequestParam String path) {
        if (path.isEmpty() || path.trim().equals("")) {
            path = username;
        }
        
        //ADD ERROR MESSAGES!!!
        
        if (accountRepository.checkIfUserExists(username)
                || accountRepository.checkIfPathExists(path)) {
            return "redirect:/register";
        }
        
        path = path.replaceAll("/", "").trim();
        
        if (path.isEmpty()) {
            path = username;
        }
        
        Account a = new Account(name,
                                username,
                                passwordEncoder.encode(password),
                                path,
                                -1L,
                                new ArrayList<>(),
                                new ArrayList<>(),
                                new ArrayList<>(),
                                new ArrayList<>(),
                                new ArrayList<>(),
                                new ArrayList<>(),
                                new ArrayList<>());
        accountRepository.save(a);
        
        return "redirect:/profile";
    }
}
