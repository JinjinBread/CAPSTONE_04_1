package univcapstone.employmentsite.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

@Data
@Getter
@Setter
public class PostDto {
    private Long postId;
    private Long userId;
    private String title;
    private String content;
    private String fileName;
}
