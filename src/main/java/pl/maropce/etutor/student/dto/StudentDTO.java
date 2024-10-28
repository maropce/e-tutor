package pl.maropce.etutor.student.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.maropce.etutor.lesson.dto.LessonDTO;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class StudentDTO {
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String discord;
    private String about;
    private String classType;

    //private List<LessonDTO> lessons;
}
