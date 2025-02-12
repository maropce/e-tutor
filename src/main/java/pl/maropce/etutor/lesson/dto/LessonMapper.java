package pl.maropce.etutor.lesson.dto;

import pl.maropce.etutor.lesson.Lesson;
import pl.maropce.etutor.student.dto.StudentDTO;
import pl.maropce.etutor.student.dto.StudentMapper;

public class LessonMapper {

    public static LessonDTO toDTO(Lesson lesson) {

        return LessonDTO.builder()
                .id(lesson.getId())
                .title(lesson.getTitle())
                .startDateTime(lesson.getStartDateTime())
                .endDateTime(lesson.getEndDateTime())
                .studentId(lesson.getStudent().getId())
                .build();
    }

    public static Lesson toEntity(LessonDTO lessonDTO, StudentDTO studentDTO) {

        return Lesson.builder()
                .id(lessonDTO.getId())
                .title(lessonDTO.getTitle())
                .startDateTime(lessonDTO.getStartDateTime())
                .endDateTime(lessonDTO.getEndDateTime())
                .student(StudentMapper.toEntity(studentDTO))
                .build();
    }
}
