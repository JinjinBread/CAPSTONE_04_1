package univcapstone.employmentsite.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @NotEmpty
    private String title;

    @NotNull
    private String content;

    @Nullable
    private String fileName;

    @Column(name = "user_id")
    private String userId;

    @CreatedDate
    private LocalDate date;

    public Post(String title, String content, String fileName, String userId) {
        this.title = title;
        this.content = content;
        this.fileName = fileName;
        this.userId = userId;
    }
}
