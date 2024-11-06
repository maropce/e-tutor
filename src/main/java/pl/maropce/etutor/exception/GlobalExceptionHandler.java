package pl.maropce.etutor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ExceptionResolver> handleBaseException(BaseException ex) {
        ExceptionResolver exceptionResolver = new ExceptionResolver(ex);

        return ResponseEntity
                .status(exceptionResolver.getStatusCode())
                .body(exceptionResolver);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResolver> handleValidationExceptions(MethodArgumentNotValidException ex) {
        final String message = "Invalid Arguments";

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        BaseException baseException = new BaseException(HttpStatus.BAD_REQUEST, message);
        ExceptionResolver exceptionResolver = new InvalidArgumentsExceptionResolver(baseException, errors);

        return ResponseEntity
                .status(exceptionResolver.getStatusCode())
                .body(exceptionResolver);
    }


}
