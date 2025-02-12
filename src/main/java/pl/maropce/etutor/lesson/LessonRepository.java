package pl.maropce.etutor.lesson;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.maropce.etutor.student.Student;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    List<Lesson> findAllByStudent(Student student);

    Optional<Lesson> findFirstByStartDateTimeAfterOrderByStartDateTimeAsc(LocalDateTime startDateTime);

    @Query("SELECT COUNT(l) > 0 FROM Lesson l WHERE " +
            "(:startDateTime < l.endDateTime AND :endDateTime > l.startDateTime)")
    boolean existsOverlappingLesson(@Param("startDateTime") LocalDateTime startDateTime,
                                    @Param("endDateTime") LocalDateTime endDateTime);

    @Query("SELECT COUNT(l) > 0 FROM Lesson l WHERE " +
            "(:startDateTime < l.endDateTime AND :endDateTime > l.startDateTime) " +
            "AND l.id != :excludedLessonId")
    boolean existsOverlappingLessonExcludingLessonById(@Param("startDateTime") LocalDateTime startDateTime,
                                    @Param("endDateTime") LocalDateTime endDateTime,
                                    @Param("excludedLessonId") Long excludedLessonId);

    List<Lesson> findAllByStartDateTimeBetween(LocalDateTime start, LocalDateTime end);

}

