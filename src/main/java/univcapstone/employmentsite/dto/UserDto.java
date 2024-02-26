package univcapstone.employmentsite.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class UserDto {

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;

    @NotEmpty
    private String nickname;

    @NotEmpty
    private String email;

    @NotEmpty
    private String name;
}
