package univcapstone.employmentsite.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PostDto {

    private Long postId;

    @NotEmpty
    private Long userId;

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    @Nullable
    private String fileName;
}
