package wanhaface.controllers;

import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import wanhaface.data.Account;
import wanhaface.data.AccountRepository;
import wanhaface.data.FriendRequest;
import wanhaface.data.FriendRequestRepository;

/**
 *
 * @author Eemeli
 */
@Controller
public class ProfileController {
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private FriendRequestRepository friendRequestRepository;
    
    @GetMapping("/profile")
    public String profile(Model model) {
        setProfileAttributes(model, getOwnAccount().getPath());
        return "profile";
    }
    
    @GetMapping("/profile/{path}")
    public String profilePath(Model model, @PathVariable String path) {
        setProfileAttributes(model, path);
        return "profile";
    }
    
    @Transactional
    @PostMapping("/request/{path}")
    public String sendFriendRequest(@PathVariable String path) {
        Account ownAccount = getOwnAccount();
        Account account = accountRepository.findByPath(path);
        
        boolean notFriends = !ownAccount.getFriends().contains(account);
        boolean requestNotSent = !friendRequestRepository.requestSent(ownAccount.getUsername(), account.getUsername());
        
        if (notFriends && requestNotSent) {
            friendRequestRepository.save(
                    new FriendRequest(ownAccount.getUsername(), 
                                    ownAccount.getName(),
                                    ownAccount.getPath(),
                                    account.getUsername(), 
                                    new Date(System.currentTimeMillis())));
        }
        
        return "redirect:/profile/" + path;
    }
    
    @Transactional
    @PostMapping("/accept/{path}")
    public String accceptFriendRequest(@PathVariable String path) {
        Account ownAccount = getOwnAccount();
        Account account = accountRepository.findByPath(path);
        
        friendRequestRepository.deleteByFromUsernameAndToUsername(account.getUsername(), ownAccount.getUsername());
        ownAccount.getFriends().add(account);
        account.getFriends().add(ownAccount);
        
        accountRepository.save(ownAccount);
        accountRepository.save(account);
        
        return "redirect:/profile";
    }
    
    @Transactional
    @PostMapping("/decline/{path}")
    public String declineFriendRequest(@PathVariable String path) {
        Account ownAccount = getOwnAccount();
        Account account = accountRepository.findByPath(path);
        
        friendRequestRepository.deleteByFromUsernameAndToUsername(account.getUsername(), ownAccount.getUsername());
        
        return "redirect:/profile";
    }
    
    private Account getOwnAccount() {
        String ownUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return accountRepository.findByUsername(ownUsername);
    }
    
    private void setProfileAttributes(Model model, String path) {
        Account ownAccount = getOwnAccount();
        String ownUsername = ownAccount.getUsername();
        Account account = accountRepository.findByPath(path);
        String username = account.getUsername();
        
        boolean requestSent = ownAccount.getFriends().contains(account)
                || friendRequestRepository.requestSent(ownUsername, username);
        boolean isOwnProfile = ownAccount.getUsername().equals(username);
        
        model.addAttribute("title", "Profile: " + username);
        model.addAttribute("name", account.getName());
        model.addAttribute("username", "@" + username);
        model.addAttribute("requestSent", requestSent);
        model.addAttribute("isOwnProfile", isOwnProfile);
        model.addAttribute("path", path);
        model.addAttribute("friendRequests", friendRequestRepository.findAllByToUsername(ownUsername));
        model.addAttribute("friends", account.getFriends());
    }
}
