package univcapstone.employmentsite.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReplyPostDto {

    @NotNull
    private Long userId;

    @Nullable
    private Long parentReplyId; //대댓글의 부모 댓글 id

    @NotEmpty
    private String replyContent;

}
