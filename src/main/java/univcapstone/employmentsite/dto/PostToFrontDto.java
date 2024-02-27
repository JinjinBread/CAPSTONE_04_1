package univcapstone.employmentsite.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import univcapstone.employmentsite.domain.Reply;
import univcapstone.employmentsite.domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter@Setter
@NoArgsConstructor
public class PostToFrontDto {
    private Long postId;
    private List<ReplyToFrontDto> replies = new ArrayList<>();
    private String category;
    private String title;
    private String content;
    private String fileName;
    private User user;
    private LocalDateTime date;

    public PostToFrontDto(Long postId, List<ReplyToFrontDto> replies, String category, String title, String content, String fileName, User user, LocalDateTime date) {
        this.postId = postId;
        this.replies = replies;
        this.category = category;
        this.title = title;
        this.content = content;
        this.fileName = fileName;
        this.user = user;
        this.date = date;
    }
}
