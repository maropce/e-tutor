package pl.maropce.etutor.lesson;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.maropce.etutor.lesson.dto.LessonDTO;
import pl.maropce.etutor.lesson.exception.LessonNotFoundException;
import pl.maropce.etutor.lesson.exception.LessonTimesOverlapException;
import pl.maropce.etutor.student.exception.StudentNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {


    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping
    public ResponseEntity<List<LessonDTO>> findAll() {
        return ResponseEntity.ok(
                lessonService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<LessonDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                lessonService.findById(id));
    }

    @GetMapping("/student/{studentId}")
    @Operation(
            summary = "Get all lessons for a specific student",
            description = "Retrieves a list of all lessons associated with the specified student ID.",
            parameters = {
                    @Parameter(name = "studentId", description = "ID of the student whose lessons are to be retrieved", required = true)
            },
            responses = {
            @ApiResponse(responseCode = "200", description = "Lessons retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Student not found"),
    })
    public ResponseEntity<List<LessonDTO>> findAllByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(
                lessonService.findAllByStudent(studentId));
    }

    @PostMapping("/create")
    @Operation(
            summary = "Create a new lesson",
            description = "Creates a new lesson for the specified student with start and end date-time.",
            parameters = {
                    @Parameter(name = "studentId", description = "ID of the student", required = true),
                    @Parameter(name = "startDateTime", description = "Start date and time of the lesson (format: yyyy-MM-dd'T'HH:mm)", required = true),
                    @Parameter(name = "endDateTime", description = "End date and time of the lesson (format: yyyy-MM-dd'T'HH:mm)", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lesson created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid lesson dates. Probably start date is after end date"),
                    @ApiResponse(responseCode = "409", description = "Lesson times overlap with an existing lesson")
            }
    )
    public ResponseEntity<LessonDTO> createNewLesson(
            @RequestParam Long studentId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime) {

        return ResponseEntity.ok(
                lessonService.save(studentId, startDateTime, endDateTime));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable Long id) {
        lessonService.deleteById(id);
    }
}
