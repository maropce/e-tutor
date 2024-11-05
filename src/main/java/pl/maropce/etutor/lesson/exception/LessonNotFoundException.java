package pl.maropce.etutor.lesson.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.maropce.etutor.exception.BaseException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LessonNotFoundException extends BaseException {

    public LessonNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "Lesson with id " + id + " not found");
    }
}
