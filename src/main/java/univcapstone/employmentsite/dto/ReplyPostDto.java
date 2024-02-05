package univcapstone.employmentsite.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

@Data
@Getter
@Setter
public class ReplyPostDto {
    private String userId;
    private String refId;
    private String replyContent;
    private Long replyRefId;
}
