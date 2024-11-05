package pl.maropce.etutor.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ExceptionResolver {

    private int statusCode;
    private String message;

    public ExceptionResolver(BaseException baseException) {
        this.statusCode = baseException.getStatusCode().value();
        this.message = baseException.getMessage();
    }
}
