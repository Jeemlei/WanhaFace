package wanhaface.data;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
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
public class ImageObject extends AbstractPersistable<Long> {
    
    @ManyToOne
    private Account owner;
    
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] bytes;
    
}