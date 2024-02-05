package univcapstone.employmentsite.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long replyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @NotNull
    private Long userId;

    //댓글, 대댓글을 묶어주는 id, self join
    @OneToMany(mappedBy = "replyId", orphanRemoval = true)
    private List<Reply> replies;

    @NotNull
    private String replyContent;

    @CreatedDate
    private LocalDate date;

//    public Reply(Long postId,
//                 String ReplyContent,
//                 String userLoginId,
//                 Long replyRefId) {
//        this.post.setPostId(postId);
//        this.replyContent = ReplyContent;
//        this.userLoginId = userLoginId;
//        this.refId = replyRefId;
//    }

}
