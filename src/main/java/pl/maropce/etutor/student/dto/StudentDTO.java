package pl.maropce.etutor.student.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
}
