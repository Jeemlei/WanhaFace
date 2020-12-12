package wanhaface.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wanhaface.domain.MessageService;
import wanhaface.domain.UserService;

/**
 *
 * @author Eemeli
 */
@Controller
public class WallController {
    
    @Autowired
    private MessageService messageService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/wall/{username}")
    public String getWall(Model model, @PathVariable String username) {
        userService.setProfileAttributes(model, userService.getAccountByUsername(username).getPath());
        model.addAttribute("messages", messageService.getMessages(userService.getAccountByUsername(username)));
        return "wall";
    }
    
    @PostMapping("/wall/{username}/message")
    public String sendMessage(@PathVariable String username, @RequestParam String message) {
        messageService.newMessage(username, message);
        return "redirect:/wall/" + username;
    }
}
