package wanhaface.domain;

import java.util.Date;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import wanhaface.data.Account;
import wanhaface.data.AccountRepository;
import wanhaface.data.FriendRequest;
import wanhaface.data.FriendRequestRepository;

/**
 *
 * @author Eemeli
 */
@Transactional
@Service
public class UserService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Autowired
    private ImageService imageService;

    public Account getOwnAccount() {
        String ownUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return accountRepository.findByUsername(ownUsername);
    }

    public Account getAccountByPath(String path) {
        return accountRepository.findByPath(path);
    }

    public Account getAccountByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    public void setProfileAttributes(Model model, String path) {
        Account ownAccount = getOwnAccount();
        String ownUsername = ownAccount.getUsername();
        Account account = accountRepository.findByPath(path);
        String username = account.getUsername();

        boolean requestSent = ownAccount.getFriends().contains(account)
                || friendRequestRepository.requestSent(ownUsername, username);
        boolean isOwnProfile = ownAccount.getUsername().equals(username);

        model.addAttribute("title", "Profile: " + username);
        model.addAttribute("name", account.getName());
        model.addAttribute("atusername", "@" + username);
        model.addAttribute("username", username);
        model.addAttribute("requestSent", requestSent);
        model.addAttribute("isOwnProfile", isOwnProfile);
        model.addAttribute("fullAlbum", imageService.fullAlbum(account));
        model.addAttribute("path", path);
        model.addAttribute("friendRequests", friendRequestRepository.findAllByToUsername(ownUsername));
        model.addAttribute("friends", account.getFriends());
    }

    public void setEditViewAttributes(Model model) {
        Account account = getOwnAccount();
        model.addAttribute("path", account.getPath());
        model.addAttribute("name", account.getName());
        model.addAttribute("isOwnProfile", true);
        model.addAttribute("fullAlbum", imageService.fullAlbum(account));
        model.addAttribute("username", account.getUsername());
        model.addAttribute("edit", true);
        model.addAttribute("ids", imageService.getImageIds(account));
    }

    public void setProfilePic(Long id) {
        getOwnAccount().setProfilePicId(id);
    }

    public void sendFriendRequest(String path) {
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
    }
    
    public void acceptFriend(String path) {
        Account ownAccount = getOwnAccount();
        Account account = accountRepository.findByPath(path);

        friendRequestRepository.deleteByFromUsernameAndToUsername(account.getUsername(), ownAccount.getUsername());
        ownAccount.getFriends().add(account);
        account.getFriends().add(ownAccount);

        accountRepository.save(ownAccount);
        accountRepository.save(account);
    }
    
    public void declineFriend(String path) {
        Account ownAccount = getOwnAccount();
        Account account = accountRepository.findByPath(path);

        friendRequestRepository.deleteByFromUsernameAndToUsername(account.getUsername(), ownAccount.getUsername());
    }
}
