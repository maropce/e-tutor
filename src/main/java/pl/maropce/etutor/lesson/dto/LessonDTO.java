package pl.maropce.etutor.lesson.dto;

import jakarta.persistence.OneToOne;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import pl.maropce.etutor.student.Student;
import pl.maropce.etutor.student.dto.StudentDTO;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class LessonDTO {

    private Long id;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private StudentDTO student;
}
