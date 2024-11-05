package pl.maropce.etutor.student.exception;

import org.springframework.http.HttpStatus;
import pl.maropce.etutor.exception.BaseException;

//@ResponseStatus(HttpStatus.NOT_FOUND)
public class StudentNotFoundException extends BaseException {

    public StudentNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "Student with id " + id + " not found");
    }

}
