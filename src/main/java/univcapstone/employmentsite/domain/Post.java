package univcapstone.employmentsite.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter@Setter
@NoArgsConstructor
@ToString
public class Post {
    @Id
    private Long postId;
    private String title;
    private String content;
    private String fileName;
    private String userId;
    private LocalDate date;

    public Post(Long postId, String title, String content, String fileName, String id) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.fileName = fileName;
        this.userId = id;
        this.date= LocalDate.now();
    }
}
