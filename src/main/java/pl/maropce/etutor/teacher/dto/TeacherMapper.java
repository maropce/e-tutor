package pl.maropce.etutor.teacher.dto;

import pl.maropce.etutor.student.dto.StudentMapper;
import pl.maropce.etutor.teacher.Teacher;

public class TeacherMapper {


    public static TeacherDTO toDTO(Teacher teacher) {

        return TeacherDTO.builder()
                .id(teacher.getId())
                .firstName(teacher.getFirstName())
                .students(teacher.getStudents()
                        .stream()
                        .map(StudentMapper::toDTO)
                        .toList())
                .build();
    }

    public static Teacher toEntity(TeacherDTO teacherDTO) {
        return Teacher.builder()
                .id(teacherDTO.getId())
                .firstName(teacherDTO.getFirstName())
                .students(teacherDTO.getStudents()
                        .stream()
                        .map(StudentMapper::toEntity)
                        .toList())
                .build();
    }
}
