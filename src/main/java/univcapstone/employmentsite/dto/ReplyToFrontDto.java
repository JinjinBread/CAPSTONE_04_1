package univcapstone.employmentsite.dto;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import univcapstone.employmentsite.domain.Post;
import univcapstone.employmentsite.domain.User;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReplyToFrontDto {
    private Long replyId;
    private Post post;
    private User user;
    private Long parentReplyId;
    private String replyContent;
    private LocalDateTime date;

    public ReplyToFrontDto(Long replyId, Post post, User user, Long parentReplyId, String replyContent, LocalDateTime date) {
        this.replyId = replyId;
        this.post = post;
        this.user = user;
        this.parentReplyId = parentReplyId;
        this.replyContent = replyContent;
        this.date = date;
    }
}
