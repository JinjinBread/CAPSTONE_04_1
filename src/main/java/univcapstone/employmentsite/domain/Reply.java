package univcapstone.employmentsite.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.repository.cdi.Eager;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter@Setter
@ToString
@NoArgsConstructor
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long replyId;

    @ManyToOne
    @JoinColumn(name="post_id")
    private Post post;

    @NotNull
    private Long id;
    @NotNull
    private String userLoginId;
    @Nullable
    private Long refId;
    @NotNull
    private String replyCotent;
    @CreatedDate
    private LocalDate date;

    public Reply(Long postId,
                 String ReplyContent,
                 String userLoginId,
                 Long replyRefId){
        this.post.setPostId(postId);
        this.replyCotent=ReplyContent;
        this.userLoginId=userLoginId;
        this.refId=replyRefId;
    }

}
