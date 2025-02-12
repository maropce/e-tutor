package pl.maropce.etutor.teacher;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.maropce.etutor.config.AppConfig;
import pl.maropce.etutor.teacher.dto.CreateTeacherRequest;
import pl.maropce.etutor.teacher.dto.TeacherDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherService teacherService;
    private final AppConfig appConfig;

    public TeacherController(TeacherService teacherService, AppConfig appConfig) {
        this.teacherService = teacherService;
        this.appConfig = appConfig;
    }

    @GetMapping
    public ResponseEntity<List<TeacherDTO>> getAllTeachers() {
        return ResponseEntity.ok(teacherService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDTO> getTeacherById(@PathVariable Long id) {
        return ResponseEntity.ok(teacherService.findById(id));
    }

    @PostMapping
    public ResponseEntity<TeacherDTO> createTeacher(@Valid @RequestBody CreateTeacherRequest request) throws URISyntaxException {

        TeacherDTO save = teacherService.save(request);

        String applicatoinURL = appConfig.getApplicationURL();
        applicatoinURL += "api/teachers/" + save.getId();

        return ResponseEntity.created(new URI(applicatoinURL))
                .body(save);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TeacherDTO> updateTeacher(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        TeacherDTO update = teacherService.update(id, updates);

        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/{id}")
    public void deleteTeacher(@PathVariable Long id) {
        teacherService.deleteById(id);
    }
}
