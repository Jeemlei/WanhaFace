package wanhaface.data;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Eemeli
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
    
    List<Comment> findByImgORmsg(boolean imgORmsg);
    List<Comment> findByImage(ImageObject image, Pageable pageable);
    List<Comment> findByMessage(Message message, Pageable pageable);
}
