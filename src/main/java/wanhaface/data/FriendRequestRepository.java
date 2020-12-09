package wanhaface.data;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Eemeli
 */
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    
    List<FriendRequest> findAllByToUsername(String toUsername);
    Long deleteByFromUsernameAndToUsername(String fromUsername, String toUsername);
    
    @Query("select count(r)>0 from FriendRequest r where (r.fromUsername = ?1 and r.toUsername = ?2) or (r.fromUsername = ?2 and r.toUsername = ?1)")
    boolean requestSent(String username1, String username2);
}
