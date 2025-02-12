package pl.maropce.etutor.lesson.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.maropce.etutor.exception.BaseException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidLessonDates extends BaseException {
    public InvalidLessonDates() {
        super(HttpStatus.BAD_REQUEST, "Invalid dates. Check if your start date is before the end date");
    }
}
