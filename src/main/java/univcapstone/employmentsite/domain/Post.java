package univcapstone.employmentsite.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    //댓글
    @OneToMany(mappedBy = "post",cascade = CascadeType.REMOVE)
    private List<Reply> replies=new ArrayList<>();

    @NotEmpty
    private String title;

    @NotNull
    private String content;

    @Nullable
    private String fileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @CreatedDate
    private LocalDate date;

    public Post(String title, String content, String fileName) {
        this.title = title;
        this.content = content;
        this.fileName = fileName;
    }
}
