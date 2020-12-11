package wanhaface.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import wanhaface.data.Account;
import wanhaface.data.ImageObject;
import wanhaface.data.ImageObjectRepository;

/**
 *
 * @author Eemeli
 */
@Service
public class ImageService {
    
    @Autowired
    private ImageObjectRepository imageRepository;
    
    @Autowired
    private UserService userService;
    
    @Transactional
    public boolean saveImage(Account owner, MultipartFile file) throws IOException {
        if (file.getContentType().equals("image/gif")
                || file.getContentType().equals("image/jpeg")
                || file.getContentType().equals("image/png")) {
            ImageObject img = new ImageObject();
            img.setOwner(owner);
            img.setBytes(file.getBytes());
            imageRepository.save(img);
            return true;
        }
        return false;
    }
    
    @Transactional
    public byte[] getImage(Long id) {
        return imageRepository.getOne(id).getBytes();
    }
    
    public List<Long> getImageIds(Account account) {
        List<ImageObject> images = imageRepository.findByOwner(account);
        List<Long> ids = new ArrayList<>();
        for (ImageObject image : images) {
            ids.add(image.getId());
        }
        return ids;
    }
    
    public void setAlbumViewAttributes(Model model, String username) {
        Account ownAccount = userService.getOwnAccount();
        String ownUsername = ownAccount.getUsername();
        Account account = userService.getAccountByUsername(username);
        
        boolean isOwnProfile = ownUsername.equals(username);
        
        model.addAttribute("path", account.getPath());
        model.addAttribute("name", account.getName());
        model.addAttribute("isOwnProfile", isOwnProfile);
        model.addAttribute("username", ownUsername);
        model.addAttribute("edit", false);
        model.addAttribute("ids", getImageIds(account));
    }
}
