package wanhaface.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
public class Message extends AbstractPersistable<Long> {
    
    private String content;
    private Date time;
    
    @ManyToOne
    private Account sender;
    
    @ManyToOne
    private Account owner;
    
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Account> likedBy = new ArrayList<>();
}
