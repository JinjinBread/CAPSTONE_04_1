package univcapstone.employmentsite.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@ToString(exclude = "post")
@EntityListeners(AuditingEntityListener.class)
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long replyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private Long userId;
    //대댓글의 부모 댓글 Id
    private Long parentReplyId;
    private String replyContent;

    @CreatedDate
    private LocalDate date;

    @Builder
    public Reply(Post post, Long userId, Long parentReplyId, String replyContent) {
        this.post = post;
        this.userId = userId;
        this.parentReplyId = parentReplyId;
        this.replyContent = replyContent;
    }

}
