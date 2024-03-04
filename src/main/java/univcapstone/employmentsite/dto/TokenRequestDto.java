package univcapstone.employmentsite.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class TokenRequestDto {

    @NotEmpty
    private String accessToken;

    @NotEmpty
    private String refreshToken;
}
