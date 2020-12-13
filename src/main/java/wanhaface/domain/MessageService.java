package wanhaface.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import wanhaface.data.Account;
import wanhaface.data.Message;
import wanhaface.data.MessageRepository;

/**
 *
 * @author Eemeli
 */
@Transactional
@Service
public class MessageService {
    
    @Autowired
    private MessageRepository messageRepository;
    
    @Autowired
    private UserService userService;
    
    public void newMessage(String sendTo, String content) {
        messageRepository.save(new Message(content, 
                                new Date(System.currentTimeMillis()),
                                userService.getOwnAccount(),
                                userService.getAccountByUsername(sendTo), 
                                new ArrayList<>()));
    }
    
    public void likeMessage(Long id) {
        Message message = messageRepository.findById(id).get();
        List<Account> likedBy = message.getLikedBy();
        Account ownAccount = userService.getOwnAccount();
        for (Account account : likedBy) {
            if (account.getUsername().equals(ownAccount.getUsername())) {
                return;
            }
        }
        if (!message.getOwner().equals(ownAccount)) {
            likedBy.add(ownAccount);
        }
    }
    
    public List<Message> getMessages(Account owner) {
        Pageable pageable = PageRequest.of(0, 25, Sort.by("time").descending());
        
        return messageRepository.findByOwner(owner, pageable);
    }
}
