package univcapstone.employmentsite.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
public class Post {
    @Id
    private Long post_id;
    private String title;
    private String content;
    private String fileName;
    private String id;
    private Date date;
}
