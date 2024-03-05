package univcapstone.employmentsite.config.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorMessage {
    private int code;
    private String message;

    public ErrorMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }
}