package wanhaface.data;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Eemeli
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
    
    Account findByUsername(String username);
    List<Account> findByUsernameLikeIgnoreCaseOrNameLikeIgnoreCase(String username, String name);
    Account findByPath(String path);
    
    @Query("select count(a)>0 from Account a where a.username = ?1")
    boolean checkIfUserExists(String username);
    @Query("select count(a)>0 from Account a where a.path = ?1")
    boolean checkIfPathExists(String path);
}
