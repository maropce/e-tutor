package pl.maropce.etutor.lesson.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDateTime;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDateTime;
    @JsonIgnore
    private StudentDTO student;
}
