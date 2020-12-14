package wanhaface.data;

import java.util.Date;
import javax.persistence.Entity;
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
public class Comment extends AbstractPersistable<Long> {
    
    private boolean imgORmsg;
    private String content;
    private Date time;
    
    @ManyToOne
    private Account from;
    
    @ManyToOne
    private ImageObject image;
    
    @ManyToOne
    private Message message;
}
