package univcapstone.employmentsite.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Reply> replies = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Bookmark> bookmark = new ArrayList<>();

    private String category;
    private String title;
    private String content;
    private String fileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @CreatedDate
    private LocalDateTime date;

    public Post(Post post, Long postId) {

    }
    @Builder
    public Post(User user, String title, String content, String fileName,String category) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.fileName = fileName;
        this.category=category;
    }

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

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", replies=" + replies +
                ", category='" + category + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", fileName='" + fileName + '\'' +
                ", date=" + date +
                '}';
    }
}
