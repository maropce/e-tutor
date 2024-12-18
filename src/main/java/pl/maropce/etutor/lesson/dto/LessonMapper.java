package pl.maropce.etutor.lesson.dto;

import pl.maropce.etutor.lesson.Lesson;
import pl.maropce.etutor.student.dto.StudentMapper;

public class LessonMapper {

    public static LessonDTO toDTO(Lesson lesson) {

        return LessonDTO.builder()
                .id(lesson.getId())
                .startDateTime(lesson.getStartDateTime())
                .endDateTime(lesson.getEndDateTime())
                .student(StudentMapper.toDTO(lesson.getStudent()))
                .build();
    }

    public static Lesson toEntity(LessonDTO lessonDTO) {

        return Lesson.builder()
                .id(lessonDTO.getId())
                .startDateTime(lessonDTO.getStartDateTime())
                .endDateTime(lessonDTO.getEndDateTime())
                .student(StudentMapper.toEntity(lessonDTO.getStudent()))
                .build();
    }
}
