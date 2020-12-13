package wanhaface.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import wanhaface.data.Account;
import wanhaface.domain.ImageService;
import wanhaface.domain.UserService;

/**
 *
 * @author Eemeli
 */
@Controller
public class ProfileController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/profile")
    public String profile(Model model) {
        userService.setProfileAttributes(model, userService.getOwnAccount().getPath());
        return "profile";
    }
    
    @GetMapping("/profile/edit")
    public String editProfilePic(Model model) {
        userService.setEditViewAttributes(model);
        return "images";
    }
    
    @GetMapping("/profile/{path}")
    public String profilePath(Model model, @PathVariable String path) {
        userService.setProfileAttributes(model, path);
        return "profile";
    }
    
    @PostMapping("/profile/setProfilePic/{id}")
    public String setProfilePic(@PathVariable Long id) {
        userService.setProfilePic(id);
        return "redirect:/profile";
    }
    
    @PostMapping("/request/{path}")
    public String sendFriendRequest(@PathVariable String path) {
        userService.sendFriendRequest(path);
        path = path.replaceAll("#", "%23").replaceAll("/", "");
        return "redirect:/profile/" + path;
    }
    
    @PostMapping("/accept/{path}")
    public String accceptFriendRequest(@PathVariable String path) {
        userService.acceptFriend(path);
        return "redirect:/profile";
    }
    
    @PostMapping("/decline/{path}")
    public String declineFriendRequest(@PathVariable String path) {
        userService.declineFriend(path);
        return "redirect:/profile";
    }
}
