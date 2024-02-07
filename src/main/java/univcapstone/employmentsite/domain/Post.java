package univcapstone.employmentsite.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    //댓글
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Reply> replies = new ArrayList<>();

    private String title;
    private String content;
    private String fileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @CreatedDate
    private LocalDate date;

    @Builder
    public Post(User user, String title, String content, String fileName) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.fileName = fileName;
    }

    @Builder
    public Post(String title, String content, String fileName) {
        this.title = title;
        this.content = content;
        this.fileName = fileName;
    }
}
