package pl.maropce.etutor.exception;

import lombok.Getter;
import lombok.Setter;

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
