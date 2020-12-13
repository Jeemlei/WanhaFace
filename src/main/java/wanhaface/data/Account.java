package wanhaface.data;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author Eemeli
 */
@Data @AllArgsConstructor @NoArgsConstructor
@Entity
public class Account extends AbstractPersistable<Long> {
    
    private String name;
    private String username;
    private String password;
    private String path;
    private Long profilePicId;
    
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Account> friends = new ArrayList<>();
    
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<ImageObject> images = new ArrayList<>();
    
    @ManyToMany(mappedBy = "likedBy", fetch = FetchType.LAZY)
    private List<ImageObject> likedImages = new ArrayList<>();
    
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<Message> wall = new ArrayList<>();
    
    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    private List<Message> sentMessages = new ArrayList<>();
    
    @ManyToMany(mappedBy = "likedBy", fetch = FetchType.LAZY)
    private List<Message> likedMessages = new ArrayList<>();
}
