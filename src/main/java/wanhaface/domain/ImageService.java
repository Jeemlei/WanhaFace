package wanhaface.domain;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import wanhaface.data.Account;
import wanhaface.data.Comment;
import wanhaface.data.CommentRepository;
import wanhaface.data.ImageObject;
import wanhaface.data.ImageObjectRepository;

/**
 *
 * @author Eemeli
 */
@Transactional
@Service
public class ImageService {
    
    @Autowired
    private ImageObjectRepository imageRepository;
    
    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private UserService userService;
    
    public boolean saveImage(String description, Account owner, MultipartFile file) throws IOException {
        if (file.getContentType().equals("image/gif")
                || file.getContentType().equals("image/jpeg")
                || file.getContentType().equals("image/png")) {
            ImageObject img = new ImageObject();
            img.setDescription(description);
            img.setOwner(owner);
            img.setBytes(file.getBytes());
            imageRepository.save(img);
            return true;
        }
        return false;
    }
    
    //Jee jee kiva spagetti...
    public void likeImage(Long id) {
        ImageObject img = imageRepository.findById(id).get();
        List<Account> likedBy = img.getLikedBy();
        Account ownAccount = userService.getOwnAccount();
        for (Account account : likedBy) {
            if (account.getUsername().equals(ownAccount.getUsername())) {
                return;
            }
        }
        if (!img.getOwner().equals(ownAccount)) {
            likedBy.add(ownAccount);
        }
    }
    
    public void postComment(Long image, String content) {
        if (content.isEmpty()) return;
        commentRepository.save(new Comment(true, 
                                            content, 
                                            new Date(System.currentTimeMillis()),
                                            userService.getOwnAccount(), 
                                            imageRepository.getOne(image),
                                            null));
    }
    
    public List<Comment> getComments(Long image) {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("time").descending());
        return commentRepository.findByImage(imageRepository.getOne(image), pageable);
    }
    
    public ImageObject getOneImage(Long id) {
        return imageRepository.findById(id).get();
    }
    
    public void delete(Long id) {
        imageRepository.deleteById(id);
    }
    
    public byte[] getImageData(Long id) {
        return imageRepository.getOne(id).getBytes();
    }
    
    public List<ImageObject> getImages(Account account) {
        return imageRepository.findByOwner(account);
    }
    
    public void setAlbumViewAttributes(Model model, String username) {
        Account ownAccount = userService.getOwnAccount();
        String ownUsername = ownAccount.getUsername();
        Account account = userService.getAccountByUsername(username);
        
        boolean isOwnProfile = ownUsername.equals(username);
        
        model.addAttribute("path", account.getPath());
        model.addAttribute("name", account.getName());
        model.addAttribute("isOwnProfile", isOwnProfile);
        model.addAttribute("fullAlbum", fullAlbum(account));
        model.addAttribute("username", ownUsername);
        model.addAttribute("albumOwner", username);
        model.addAttribute("edit", false);
        model.addAttribute("images", getImages(account));
    }
    
    public boolean fullAlbum(Account account) {
        return imageRepository.fullAlbum(account);
    }
}
