package univcapstone.employmentsite.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import univcapstone.employmentsite.util.AuthConstants;

@Getter
@RedisHash(value = "refreshToken", timeToLive = AuthConstants.REFRESH_TOKEN_VALID_TIME)
public class RefreshToken {

    @Id
    private String refreshToken;

    private Long userId;

    @Builder
    public RefreshToken(String refreshToken, Long userId) {
        this.refreshToken = refreshToken;
        this.userId = userId;
    }
}
