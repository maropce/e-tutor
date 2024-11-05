package pl.maropce.etutor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ExceptionResolver> handleBaseException(BaseException ex) {
        ExceptionResolver exceptionResolver = new ExceptionResolver(ex);
        return new ResponseEntity<>(exceptionResolver, ex.getStatusCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResolver> handleValidationExceptions(MethodArgumentNotValidException ex) {

        StringBuilder sb = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            sb.append(error.getField())
                    .append(": ")
                    .append(error.getDefaultMessage())
                    .append(" | ");
        });

        BaseException baseException = new BaseException(HttpStatus.BAD_REQUEST, sb.toString());
        ExceptionResolver exceptionResolver = new ExceptionResolver(baseException);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResolver);
    }


}
