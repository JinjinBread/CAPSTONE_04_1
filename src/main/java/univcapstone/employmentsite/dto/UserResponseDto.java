package univcapstone.employmentsite.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import univcapstone.employmentsite.domain.User;

@Data
public class UserResponseDto {

    private String loginId;

    private String nickname;

    @Builder
    public UserResponseDto(User user) {
        this.loginId = user.getLoginId();
        this.nickname = user.getNickname();
    }
}
