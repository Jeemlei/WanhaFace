package wanhaface.security;

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
public class Account extends AbstractPersistable<Long> {
    
    private String name;
    private String username;
    private String password;
    private String path;
}
