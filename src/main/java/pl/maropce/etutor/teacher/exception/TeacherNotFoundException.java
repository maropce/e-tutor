package pl.maropce.etutor.teacher.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.maropce.etutor.exception.BaseException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TeacherNotFoundException extends BaseException {

    public TeacherNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "Teacher with id " + id + " not found");
    }
}
