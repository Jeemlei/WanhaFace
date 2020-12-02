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
    public String registerNewAccount(   @RequestParam String username,
                                        @RequestParam String password) {
        if (accountRepository.checkIfExists(username)) {
            return "redirect:/register";
        }
        
        Account a = new Account(username, passwordEncoder.encode(password));
        accountRepository.save(a);
        
        return "redirect:/login";
    }
}
