package univcapstone.employmentsite.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class UserRequestDto {

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;

    private String nickname;

    private String email;

    private String name;
}
