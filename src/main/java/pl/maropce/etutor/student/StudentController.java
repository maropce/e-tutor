package pl.maropce.etutor.student;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);


    public StudentController(StudentService studentService, AppConfig appConfig) {
        this.studentService = studentService;
        this.appConfig = appConfig;
    }

    @GetMapping
    @Operation(
            summary = "Get all students or search by keyword",
            description = "Returns a list of all students or filters them by a keyword if provided. The keyword is used to perform a case-insensitive search across multiple fields: first name, last name, Discord username, and phone number. If the keyword matches any part of these fields, the corresponding students are returned.",
            parameters = {
                    @Parameter(name = "keyword", description = "Optional keyword to search for students")
            }
    )
    @ApiResponses(value={
            @ApiResponse(
                    responseCode = "200",
                    description = "Students retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = StudentDTO.class))
                    )
            )
    })
    public ResponseEntity<List<StudentDTO>> getAllStudents(
            @RequestParam(required = false) String keyword) {

        List<StudentDTO> studentDTOS;

        if (keyword == null || keyword.isEmpty()) {
            studentDTOS = studentService.findAll();
        } else {
            studentDTOS = studentService.findByKeyword(keyword);
        }

        return ResponseEntity.ok(studentDTOS);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get a student by ID",
            description = "This endpoint retrieves a student based on the provided ID.",
            parameters = {
                    @Parameter(name = "id", description = "ID of the student to be retrieved", required = true)
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Student retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = StudentDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Student not found",
                    content = @Content()
            )
    })
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        StudentDTO studentDTO = studentService.findById(id);

        return ResponseEntity.ok(studentDTO);
    }

    @PostMapping
    @Operation(
            summary = "Create a new student",
            description = "This endpoint creates a new student and returns the created student object."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Student created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = StudentDTO.class)
                    )
            )
    })
    public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentDTO studentDTO) throws URISyntaxException {

        logger.info("Received StudentDTO: {}", studentDTO);

        StudentDTO save = studentService.save(studentDTO);

        String applicationURL = appConfig.getApplicationURL();
        applicationURL += "api/students/" + save.getId();

        return ResponseEntity.created(new URI(applicationURL))
                .body(save);
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Update a student by ID",
            description = "This endpoint updates a student's details based on the provided ID.",
            parameters = {
                    @Parameter(name = "id", description = "ID of the student to be updated", required = true)
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Student updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = StudentDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Student not found",
                    content = @Content()
            ),
    })
    public ResponseEntity<StudentDTO> updateStudent(
            @PathVariable Long id, @RequestBody Map<String, Object> updates) {

        StudentDTO updatedStudent = studentService.update(id, updates);

        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("{id}")
    @Operation(
            summary = "Delete a student by ID",
            description = "This endpoint deletes a student based on the provided ID.",
            parameters = {
                    @Parameter(name = "id", description = "ID of the student to be deleted", required = true)
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Student deleted successfully",
                    content = @Content()
            ),
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteById(id);
    }
}
