package univcapstone.employmentsite.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import univcapstone.employmentsite.domain.Reply;
import univcapstone.employmentsite.domain.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter@Setter
@ToString
@NoArgsConstructor
public class PostToFrontDto {
    private Long postId;
    private List<ReplyToFrontDto> replies = new ArrayList<>();
    private String category;
    private String title;
    private String content;
    private String fileName;
    private Long userId;
    private String date;

    public PostToFrontDto(Long postId, List<ReplyToFrontDto> replies, String category, String title, String content, String fileName, Long userId, LocalDateTime date) {
        this.postId = postId;
        this.replies = replies;
        this.category = category;
        this.title = title;
        this.content = content;
        this.fileName = fileName;
        this.userId = userId;
        this.date = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
