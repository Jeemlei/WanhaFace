package wanhaface.data;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
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
    
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Account> friends = new ArrayList<>();
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Account> friendRequests = new ArrayList<>();
}
