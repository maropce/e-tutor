package pl.maropce.etutor.lesson;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.maropce.etutor.lesson.dto.CreateLessonRequest;
import pl.maropce.etutor.lesson.dto.LessonDTO;
import pl.maropce.etutor.lesson.dto.LessonMapper;
import pl.maropce.etutor.lesson.dto.UpdateLessonRequest;
import pl.maropce.etutor.lesson.exception.InvalidLessonDates;
import pl.maropce.etutor.lesson.exception.LessonNotFoundException;
import pl.maropce.etutor.lesson.exception.LessonTimesOverlapException;
import pl.maropce.etutor.student.Student;
import pl.maropce.etutor.student.StudentRepository;
import pl.maropce.etutor.student.exception.StudentNotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final StudentRepository studentRepository;

    public LessonService(LessonRepository lessonRepository, StudentRepository studentRepository) {
        this.lessonRepository = lessonRepository;
        this.studentRepository = studentRepository;
    }

    public List<LessonDTO> findAll(Pageable pageable) {
        return lessonRepository.findAll(pageable)
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


    public LessonDTO save(CreateLessonRequest request) {

        if(!isDateOfLessonSetProperly(request.getStartDateTime(), request.getEndDateTime())) {
            throw new InvalidLessonDates();
        }

        if (lessonRepository.existsOverlappingLesson(request.getStartDateTime(), request.getEndDateTime())) {
            throw new LessonTimesOverlapException();
        }

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new StudentNotFoundException(request.getStudentId()));

        Lesson lesson = Lesson.builder()
                .title(request.getTitle())
                .startDateTime(request.getStartDateTime())
                .endDateTime(request.getEndDateTime())
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

    public List<LessonDTO> findLessonsByMonth(LocalDate month) {
        LocalDate start = month.withDayOfMonth(1);
        LocalDate end = month.plusMonths(1).withDayOfMonth(1).minusDays(1);
        return lessonRepository.findAllByStartDateTimeBetween(start.atStartOfDay(), end.atTime(23, 59))
                .stream()
                .map(LessonMapper::toDTO)
                .toList();
    }


    public LessonDTO updateLesson(Long id, UpdateLessonRequest lessonRequest) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new LessonNotFoundException(id));

        if (lessonRepository.existsOverlappingLessonExcludingLessonById(lessonRequest.getStartDateTime(), lessonRequest.getEndDateTime(), lesson.getId())) {
            throw new LessonTimesOverlapException();
        }

        if (!isDateOfLessonSetProperly(lessonRequest.getStartDateTime(), lessonRequest.getEndDateTime())) {
            throw new InvalidLessonDates();
        }


        Student student = studentRepository.findById(lessonRequest.getStudentId()).orElseThrow(() -> new StudentNotFoundException(lessonRequest.getStudentId()));

        lesson.setTitle(lessonRequest.getTitle());
        lesson.setStartDateTime(lessonRequest.getStartDateTime());
        lesson.setEndDateTime(lessonRequest.getEndDateTime());
        lesson.setStudent(student);

        Lesson updatetedLesson = lessonRepository.save(lesson);

        return LessonMapper.toDTO(updatetedLesson);
    }

    public LessonDTO findNext() {

        Lesson lesson = lessonRepository.findFirstByStartDateTimeAfterOrderByStartDateTimeAsc(LocalDateTime.now())
                .orElseThrow(() -> new LessonNotFoundException("It looks like there is no lessons in the future"));

        return LessonMapper.toDTO(lesson);
    }
}
