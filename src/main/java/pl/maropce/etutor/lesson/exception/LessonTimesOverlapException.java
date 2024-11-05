package pl.maropce.etutor.lesson.exception;

import com.fasterxml.jackson.databind.JsonSerializable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.maropce.etutor.exception.BaseException;

@ResponseStatus(HttpStatus.CONFLICT)
public class LessonTimesOverlapException extends BaseException {

    public LessonTimesOverlapException() {
        super(HttpStatus.CONFLICT, "Lesson times overlap with an existing lesson!");
    }
}
