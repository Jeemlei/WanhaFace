package wanhaface.data;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Eemeli
 */
public interface ImageObjectRepository extends JpaRepository<ImageObject, Long> {
    
    public List<ImageObject> findByOwner(Account owner);
    
    @Query("select count(i)>9 from ImageObject i where i.owner = ?1")
    public boolean fullAlbum(Account owner);
}
