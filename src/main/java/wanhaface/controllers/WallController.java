package wanhaface.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wanhaface.data.Message;
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
    
    @GetMapping("/message/{id}/{username}")
    public String getMessage(Model model, @PathVariable Long id, @PathVariable String username) {
        userService.setProfileAttributes(model, userService.getAccountByUsername(username).getPath());
        model.addAttribute("message", messageService.getOneMessage(id));
        model.addAttribute("comments", messageService.getComments(id));
        return "message";
    }
    
    @PostMapping("/wall/{username}/message")
    public String sendMessage(@PathVariable String username, @RequestParam String message) {
        messageService.newMessage(username, message);
        return "redirect:/wall/" + username;
    }
    
    @PostMapping("/message/like/{id}")
    public String likeMessage(@PathVariable Long id, @RequestParam String redirect) {
        messageService.likeMessage(id);
        return "redirect:" + redirect;
    }
    
    @PostMapping("/message/{id}/comment")
    public String commentMessage(@PathVariable Long id, @RequestParam String content, @RequestParam String redirect) {
        messageService.postComment(id, content);
        return "redirect:/message/" + id + "/" + redirect;
    }
}
