package wanhaface.controllers;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import wanhaface.data.Account;
import wanhaface.domain.ImageService;
import wanhaface.domain.UserService;

/**
 *
 * @author Eemeli
 */
@Controller
public class ImageController {

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/images/{username}")
    public String showAlbum(Model model, @PathVariable String username) {
        imageService.setAlbumViewAttributes(model, username);
        return "images";
    }

    @GetMapping("/image/{id}")
    @ResponseBody
    public byte[] getImage(@PathVariable Long id) {
        return imageService.getImage(id);
    }

    @GetMapping("/images/profilepic/{username}")
    @ResponseBody
    public byte[] getProfilePic(@PathVariable String username) {
        Long id = userService.getAccountByUsername(username).getProfilePicId();
        if (id < 0L) {
            return new byte[0];
        }
        return imageService.getImage(id);
    }

    @PostMapping("/image/{username}")
    public String uploadImage(@PathVariable String username, 
                                @RequestParam("file") MultipartFile file,
                                @RequestParam("description") String description,
                                @RequestParam("redirect") String redirect) throws IOException {
        Account account = userService.getOwnAccount();
        if (account.getUsername().equals(username)) {
            imageService.saveImage(description, account, file);
        }
        return "redirect:" + redirect;
    }

    @PostMapping("/image/delete/{id}")
    public String uploadImage(@PathVariable Long id, @RequestParam String redirect) throws IOException {
        imageService.delete(id);
        return "redirect:" + redirect;
    }
    
    @PostMapping("/image/like/{id}")
    public String likeImage(@PathVariable Long id, @RequestParam String redirect) {
        imageService.likeImage(id);
        return "redirect:" + redirect;
    }
}
