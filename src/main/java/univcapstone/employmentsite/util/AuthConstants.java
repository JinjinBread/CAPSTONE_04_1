package univcapstone.employmentsite.util;

public class AuthConstants {

    public static final String AUTHORITIES_KEY = "auth";
    public static final String AUTH_HEADER = "Authorization";
    public static final String REFRESH_HEADER = "Refresh";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final long ACCESS_TOKEN_VALID_TIME = 1000 * 10;            // 30분
    public static final long REFRESH_TOKEN_VALID_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일
}
