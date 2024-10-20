package pl.maropce.etutor.student;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.maropce.etutor.config.AppConfig;
import pl.maropce.etutor.student.dto.StudentDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;
    private final AppConfig appConfig;


    public StudentController(StudentService studentService, AppConfig appConfig) {
        this.studentService = studentService;
        this.appConfig = appConfig;
    }

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<StudentDTO> studentDTOS = studentService.findAll();

        return ResponseEntity.ok(studentDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        StudentDTO studentDTO = studentService.findById(id);

        return ResponseEntity.ok(studentDTO);
    }

    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentDTO) throws URISyntaxException {

        StudentDTO save = studentService.save(studentDTO);

        String applicationURL = appConfig.getApplicationURL();
        applicationURL += "api/students/" + save.getId();

        return ResponseEntity.created(new URI(applicationURL))
                .body(save);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(
            @PathVariable Long id, @RequestBody Map<String, Object> updates) throws URISyntaxException {

        StudentDTO updatedStudent = studentService.update(id, updates);

        StudentDTO savedStudent = studentService.save(updatedStudent);

        return ResponseEntity.ok(savedStudent);

    }

    @ExceptionHandler(StudentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String exceptionHandler(StudentNotFoundException ex) {
        return ex.getMessage();
    }
}
