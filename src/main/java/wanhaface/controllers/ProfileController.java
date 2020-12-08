package wanhaface.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import wanhaface.data.Account;
import wanhaface.data.AccountRepository;

/**
 *
 * @author Eemeli
 */
@Controller
public class ProfileController {
    
    @Autowired
    private AccountRepository accountRepository;
    
    @GetMapping("/profile")
    public String profile(Model model) {
        String username =  SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = accountRepository.findByUsername(username);
        
        model.addAttribute("title", "Profile: " + username);
        model.addAttribute("name", account.getName());
        model.addAttribute("username", "@" + username);
        model.addAttribute("requestSent", true);
        model.addAttribute("isOwnProfile", true);
        model.addAttribute("friendRequests", account.getFriendRequests());
        
        return "profile";
    }
    
    @GetMapping("/profile/{path}")
    public String profilePath(Model model, @PathVariable String path) {
        String ownUsername =  SecurityContextHolder.getContext().getAuthentication().getName();
        Account ownAccount = accountRepository.findByUsername(ownUsername);
        
        Account account = accountRepository.findByPath(path);
        String username = account.getUsername();
        
        boolean requestSent = ownAccount.getFriends().contains(account)
                || ownAccount.getFriendRequests().contains(account)
                || account.getFriendRequests().contains(ownAccount);
        boolean isOwnProfile = ownUsername.equals(username);
        
        model.addAttribute("title", "Profile: " + username);
        model.addAttribute("name", account.getName());
        model.addAttribute("username", "@" + username);
        model.addAttribute("requestSent", requestSent);
        model.addAttribute("isOwnProfile", isOwnProfile);
        model.addAttribute("path", path);
        model.addAttribute("friendRequests", ownAccount.getFriendRequests());
        
        return "profile";
    }
    
    @PostMapping("/request/{path}")
    public String sendFriendRequest(@PathVariable String path) {
        String ownUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Account ownAccount = accountRepository.findByUsername(ownUsername);
        Account account = accountRepository.findByPath(path);
        
        List<Account> ownFriends = ownAccount.getFriends();
        List<Account> ownFriendRequest = ownAccount.getFriendRequests();
        List<Account> friendRequests = account.getFriendRequests();
        
        if (!ownFriends.contains(account)
                && !ownFriendRequest.contains(account)
                && !friendRequests.contains(ownAccount)) {
            friendRequests.add(ownAccount);
            accountRepository.save(account);
        }
        
        return "redirect:/profile/" + path;
    }
    
    //--------------------------------------------------------
    //--------------------------TODO--------------------------
    //--------------------------------------------------------
    @PostMapping("/accept/{path}")
    public String accceptFriendRequest(@PathVariable String path) {
        
        
        return "redirect:/profile";
    }
    
    @PostMapping("/decline/{path}")
    public String declineFriendRequest(@PathVariable String path) {
        
        
        return "redirect:/profile";
    }
}
