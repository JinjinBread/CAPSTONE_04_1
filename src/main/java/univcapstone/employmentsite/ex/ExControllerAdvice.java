package univcapstone.employmentsite.ex;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import univcapstone.employmentsite.ex.custom.UserAuthenticationException;

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResult userAuthExHandler(UserAuthenticationException e) {
        log.error("[userAuthExHandler] ex", e);
        return new ErrorResult("userAuthEx", e.getMessage());
    }

}
