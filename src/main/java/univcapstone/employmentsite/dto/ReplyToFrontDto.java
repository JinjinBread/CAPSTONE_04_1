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
    private Long postId;
    private Long userId;
    private Long parentReplyId;
    private String replyContent;
    private LocalDateTime date;

    public ReplyToFrontDto(Long replyId, Long postId, Long userId, Long parentReplyId, String replyContent, LocalDateTime date) {
        this.replyId = replyId;
        this.postId = postId;
        this.userId = userId;
        this.parentReplyId = parentReplyId;
        this.replyContent = replyContent;
        this.date = date;
    }
}
