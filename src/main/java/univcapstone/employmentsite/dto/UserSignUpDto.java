package univcapstone.employmentsite.dto;

import jakarta.validation.constraints.NotEmpty;


public class UserSignUpDto {

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;

    @NotEmpty
    private String name;

    @NotEmpty
    private String nickname;

    @NotEmpty
    private String email;
}
