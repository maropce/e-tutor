package pl.maropce.etutor.lesson;

import org.springframework.stereotype.Service;
import pl.maropce.etutor.lesson.dto.LessonDTO;
import pl.maropce.etutor.lesson.dto.LessonMapper;
import pl.maropce.etutor.lesson.exception.InvalidLessonDates;
import pl.maropce.etutor.lesson.exception.LessonNotFoundException;
import pl.maropce.etutor.lesson.exception.LessonTimesOverlapException;
import pl.maropce.etutor.student.Student;
import pl.maropce.etutor.student.StudentRepository;
import pl.maropce.etutor.student.exception.StudentNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final StudentRepository studentRepository;

    public LessonService(LessonRepository lessonRepository, StudentRepository studentRepository) {
        this.lessonRepository = lessonRepository;
        this.studentRepository = studentRepository;
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

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));

        return lessonRepository.findAllByStudent(student)
                .stream()
                .map(LessonMapper::toDTO)
                .toList();
    }


    public LessonDTO save(Long studentId, LocalDateTime startDateTime, LocalDateTime endDateTime) {

        if(!isDateOfLessonSetProperly(startDateTime, endDateTime)) {
            throw new InvalidLessonDates();
        }

        if (lessonRepository.existsOverlappingLesson(startDateTime, endDateTime)) {
            throw new LessonTimesOverlapException();
        }

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));

        Lesson lesson = Lesson.builder()
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .student(student)
                .build();

        Lesson save = lessonRepository.save(lesson);

        return LessonMapper.toDTO(save);
    }

    private boolean isDateOfLessonSetProperly(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return startDateTime.isBefore(endDateTime);
    }

    public void deleteById(Long id) {
        lessonRepository.deleteById(id);
    }

    public boolean existsOverlappingLesson(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return lessonRepository.existsOverlappingLesson(startDateTime, endDateTime);
    }


}
