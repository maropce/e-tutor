package pl.maropce.etutor.teacher.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.maropce.etutor.student.dto.StudentDTO;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class TeacherDTO {
    private Long id;

    @NotBlank
    @Size(min = 2, max = 50, message = "First name name must be between 2 and 50 characters")
    private String firstName;

    private List<StudentDTO> students;

}
