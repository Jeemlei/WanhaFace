package wanhaface.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    public String registrationPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerNewAccount(   @RequestParam String name,
                                        @RequestParam String username,
                                        @RequestParam String password,
                                        @RequestParam String path) {
        if (accountRepository.checkIfExists(username)) {
            return "redirect:/register";
        }
        
        if (path.isEmpty() || path.isBlank()) {
            path = username;
        }
        
        Account a = new Account(name, username, passwordEncoder.encode(password), path);
        accountRepository.save(a);
        
        return "redirect:/login";
    }
}
