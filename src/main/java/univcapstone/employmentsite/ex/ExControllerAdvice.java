package univcapstone.employmentsite.ex;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import univcapstone.employmentsite.ex.custom.UserAuthenticationException;
import univcapstone.employmentsite.util.response.BasicResponse;
import univcapstone.employmentsite.util.response.ErrorResponse;

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResponseEntity<? extends BasicResponse> userAuthExHandler(UserAuthenticationException e) {
        log.error("[userAuthExHandler] ex", e);
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

}
