package univcapstone.employmentsite.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import univcapstone.employmentsite.domain.User;

@Data
public class UserLoginForm {

    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;

    public static UserLoginForm toUserLoginForm(User user) {
        UserLoginForm userLoginForm = new UserLoginForm();
        userLoginForm.setLoginId(user.getLoginId());
        userLoginForm.setPassword(user.getPassword());
        return userLoginForm;
    }

}
