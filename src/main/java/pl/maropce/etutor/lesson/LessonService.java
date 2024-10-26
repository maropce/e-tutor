package pl.maropce.etutor.lesson;

import org.springframework.stereotype.Service;
import pl.maropce.etutor.lesson.dto.LessonDTO;
import pl.maropce.etutor.lesson.dto.LessonMapper;
import pl.maropce.etutor.lesson.exception.LessonNotFoundException;
import pl.maropce.etutor.lesson.exception.LessonTimesOverlapException;
import pl.maropce.etutor.student.Student;
import pl.maropce.etutor.student.StudentService;
import pl.maropce.etutor.student.dto.StudentDTO;
import pl.maropce.etutor.student.dto.StudentMapper;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final StudentService studentService;

    public LessonService(LessonRepository lessonRepository, StudentService studentService) {
        this.lessonRepository = lessonRepository;
        this.studentService = studentService;
    }

    public List<LessonDTO> findAll() {
        return lessonRepository.findAll()
                .stream()
                .map(LessonMapper::toDTO)
                .toList();
    }

    public LessonDTO findById(Long id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new LessonNotFoundException(id));

        return LessonMapper.toDTO(lesson);
    }

    public List<LessonDTO> findAllByStudent(Long studentId) {

        Student student = StudentMapper.toEntity(
                studentService.findById(studentId));

        return lessonRepository.findAllByStudent(student)
                .stream()
                .map(LessonMapper::toDTO)
                .toList();
    }


    public LessonDTO save(Long studentId, LocalDateTime startDateTime, LocalDateTime endDateTime) {

        if (lessonRepository.existsOverlappingLesson(startDateTime, endDateTime)) {
            throw new LessonTimesOverlapException();
        }

        StudentDTO studentDTO = studentService.findById(studentId);

        LessonDTO lessonDTO = LessonDTO.builder()
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .student(studentDTO)
                .build();

        Lesson save = lessonRepository.save(
                LessonMapper.toEntity(lessonDTO));

        return LessonMapper.toDTO(save);
    }


    public void deleteById(Long id) {
        lessonRepository.deleteById(id);
    }

    public boolean existsOverlappingLesson(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return lessonRepository.existsOverlappingLesson(startDateTime, endDateTime);
    }


}
