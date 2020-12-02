package wanhaface.security;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Eemeli
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
    
    Account findByUsername(String username);
    
    @Query("select count(a)>0 from Account a where a.username = ?1")
    boolean checkIfExists(String username);
}
