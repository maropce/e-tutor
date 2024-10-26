package pl.maropce.etutor.lesson.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidLessonDates extends RuntimeException {
    public InvalidLessonDates() {
        super("Invalid dates! Check if your start date is before the end date");
    }
}
