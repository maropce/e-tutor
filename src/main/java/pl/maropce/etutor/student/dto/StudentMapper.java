package pl.maropce.etutor.student.dto;

import pl.maropce.etutor.student.Student;

public class StudentMapper {

    public static StudentDTO toDTO(Student student) {
        StudentDTO dto = StudentDTO.builder().build();

        dto.setId(student.getId());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setEmail(student.getEmail());
        dto.setPhone(student.getPhone());
        dto.setDiscord(student.getDiscord());
        dto.setClassType(student.getClassType());
        dto.setAbout(student.getAbout());

        return dto;
    }

    public static Student toEntity(StudentDTO dto) {
        Student student = new Student();
        student.setId(dto.getId());
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setEmail(dto.getEmail());
        student.setPhone(dto.getPhone());
        student.setDiscord(dto.getDiscord());
        student.setClassType(dto.getClassType());
        student.setAbout(dto.getAbout());
        return student;
        
    }
}
