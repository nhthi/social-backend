package vn.nht.social.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> otherExceptionHandler(Exception ue,
                                                              WebRequest req){
        ErrorDetails error = new ErrorDetails();
        error.setMessage(ue.getMessage());
        error.setError(req.getDescription(false));
        error.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<ErrorDetails>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDetails> userExceptionHandler(Exception ue,
                                                              WebRequest req){
        ErrorDetails error = new ErrorDetails();
        error.setMessage(ue.getMessage());
        error.setError(req.getDescription(false));
        error.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<ErrorDetails>(error, HttpStatus.BAD_REQUEST);
    }
}
