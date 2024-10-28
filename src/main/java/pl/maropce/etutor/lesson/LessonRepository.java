package pl.maropce.etutor.lesson;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.maropce.etutor.student.Student;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    List<Lesson> findAllByStudent(Student student);

    @Query("SELECT COUNT(l) > 0 FROM Lesson l WHERE " +
            "(:startDateTime < l.endDateTime AND :endDateTime > l.startDateTime)")
    boolean existsOverlappingLesson(@Param("startDateTime") LocalDateTime startDateTime,
                                    @Param("endDateTime") LocalDateTime endDateTime);
}

