package wanhaface.data;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Eemeli
 */
public interface ImageObjectRepository extends JpaRepository<ImageObject, Long> {
    
    public List<ImageObject> findByOwner(Account owner);
}
