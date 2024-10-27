package pl.maropce.etutor.lesson;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.maropce.etutor.lesson.dto.LessonDTO;

import java.util.List;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {


    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping
    @Operation(
            summary = "Retrieve all lessons",
            description = "This endpoint retrieves all lessons available in the system.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lessons retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = LessonDTO.class))
                            )
                    )
            }
    )
    public ResponseEntity<List<LessonDTO>> findAll() {
        return ResponseEntity.ok(
                lessonService.findAll());
    }

    @GetMapping("{id}")
    @Operation(
            summary = "Get a lesson by ID",
            description = "Retrieves a lesson based on the provided ID.",
            parameters = {
                    @Parameter(name = "id", description = "ID of the lesson to retrieve", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lesson retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = LessonDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Lesson not found",
                            content = @Content()
                    )
            }
    )
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
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lessons retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = LessonDTO.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Student not found",
                            content = @Content()
                    )
            }
    )
    public ResponseEntity<List<LessonDTO>> findAllByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(
                lessonService.findAllByStudent(studentId));
    }

    @PostMapping("/create")
    @Operation(
            summary = "Create a new lesson",
            description = "Creates a new lesson for a student based on the provided student ID, start date, and end date.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Details of the lesson to create, including the student ID, start time, and end time in ISO date-time format (yyyy-MM-dd'T'HH:mm:ss)",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreateLessonRequest.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lesson created successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = LessonDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid dates. Check if your start date is before the end date",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Lesson not found",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Lesson times overlap with an existing lesson!",
                            content = @Content()
                    )
            }
    )
    public ResponseEntity<LessonDTO> createNewLesson(@RequestBody CreateLessonRequest request) {

        System.out.println(request);
        return ResponseEntity.ok(
                lessonService.save(request.getStudentId(), request.getStartDateTime(), request.getEndDateTime()));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete a lesson by ID",
            description = "Deletes a lesson based on the provided ID.",
            parameters = {
                    @Parameter(name = "id", description = "ID of the lesson to delete", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Lesson deleted successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Lesson not found",
                            content = @Content()
                    )
            }
    )
    public void deleteStudent(@PathVariable Long id) {
        lessonService.deleteById(id);
    }
}
