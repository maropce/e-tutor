package pl.maropce.etutor.lesson.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class LessonTimesOverlapException extends RuntimeException {

    public LessonTimesOverlapException() {
        super("Lesson times overlap with an existing lesson!");
    }
}
