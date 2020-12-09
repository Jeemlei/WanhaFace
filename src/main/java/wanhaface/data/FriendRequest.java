package wanhaface.data;

import java.util.Date;
import javax.persistence.Entity;
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
public class FriendRequest extends AbstractPersistable<Long> {
    
    private String fromUsername;
    private String fromName;
    private String fromPath;
    private String toUsername;
    private Date time;
}
