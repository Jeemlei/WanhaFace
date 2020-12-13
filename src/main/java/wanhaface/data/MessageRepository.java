package wanhaface.data;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Eemeli
 */
public interface MessageRepository extends JpaRepository<Message, Long> {
    
    public List<Message> findByOwner(Account owner, Pageable pageable);
}
