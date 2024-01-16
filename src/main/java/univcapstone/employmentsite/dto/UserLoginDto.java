package univcapstone.employmentsite.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import univcapstone.employmentsite.domain.User;

@Data
public class UserLoginDto {

    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;

    public static UserLoginDto toUserLoginDto(User user) {
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setLoginId(user.getLoginId());
        userLoginDto.setPassword(user.getPassword());
        return userLoginDto;
    }

}
