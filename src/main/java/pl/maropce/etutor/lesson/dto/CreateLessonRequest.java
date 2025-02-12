package pl.maropce.etutor.lesson.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CreateLessonRequest {

    @NotNull(message = "Student ID cannot be null")
    private Long studentId;

    @NotBlank
    @Size(min = 1, max = 50)
    private String title;


    @NotNull(message = "Start date and time cannot be null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDateTime;


    @NotNull(message = "End date and time cannot be null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDateTime;
}
