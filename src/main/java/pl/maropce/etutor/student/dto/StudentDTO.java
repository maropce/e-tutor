package pl.maropce.etutor.student.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class StudentDTO {
    private Long id;

    @NotBlank(message = "First name cannot be blank")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;


    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be blank")
    @Size(max = 50, message = "Email must be at most 50 characters")
    private String email;

    private String phone;

    @Size(min = 2, max = 50, message = "Discord username must be between 2 and 50 characters")
    private String discord;

    @Size(max = 200, message = "About section must be at most 200 characters")
    private String about;

    @NotBlank(message = "Class type cannot be blank")
    @Size(min = 2, max = 50, message = "Class type must be between 2 and 50 characters")
    private String classType;
}
