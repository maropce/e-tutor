package pl.maropce.etutor.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class BaseException extends RuntimeException {

    private HttpStatus statusCode;
    private String message;
}
