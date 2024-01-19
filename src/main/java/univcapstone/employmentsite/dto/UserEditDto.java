package univcapstone.employmentsite.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Data
@Getter
@Setter
public class UserEditDto {
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String pw;
    @NotEmpty
    private String nickname;
    private String photo_id;
}
