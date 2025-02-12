package pl.maropce.etutor.config;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.maropce.etutor.lesson.LessonRepository;
import pl.maropce.etutor.student.StudentRepository;
import pl.maropce.etutor.teacher.TeacherRepository;

@RestController
@RequestMapping("/api/config")
public class ConfigurationController {

    private final StudentRepository studentRepository;
    private final LessonRepository lessonRepository;
    private final TeacherRepository teacherRepository;
    private final AppConfig appConfig;

    public ConfigurationController(StudentRepository studentRepository, LessonRepository lessonRepository, TeacherRepository teacherRepository, AppConfig appConfig) {
        this.studentRepository = studentRepository;
        this.lessonRepository = lessonRepository;
        this.teacherRepository = teacherRepository;
        this.appConfig = appConfig;
    }

    @GetMapping("/reset")
    public String reset() {
        lessonRepository.deleteAll();
        studentRepository.deleteAll();
        teacherRepository.deleteAll();

        appConfig.addStudentsWithLessons();
        return "DATABASE SUCCESSFULLY SET TO DEFAULT VALUES";
    }
}
