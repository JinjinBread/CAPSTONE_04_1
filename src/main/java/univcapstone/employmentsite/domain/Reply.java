package univcapstone.employmentsite.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.repository.cdi.Eager;

import java.util.Date;

@Entity
@Getter@Setter
@ToString
public class Reply {
    @Id
    private Long reply_id;
    private Long ref_id;
    private String reply_cotent;
    private Date date;
    private Long post_id;
}
