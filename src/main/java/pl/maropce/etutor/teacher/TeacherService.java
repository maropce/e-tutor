package pl.maropce.etutor.teacher;

import org.springframework.stereotype.Service;
import pl.maropce.etutor.student.Student;
import pl.maropce.etutor.teacher.dto.CreateTeacherRequest;
import pl.maropce.etutor.teacher.dto.TeacherDTO;
import pl.maropce.etutor.teacher.dto.TeacherMapper;
import pl.maropce.etutor.teacher.exception.TeacherNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<TeacherDTO> findAll() {
        return teacherRepository.findAll()
                .stream()
                .map(TeacherMapper::toDTO)
                .toList();
    }

    public TeacherDTO findById(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException(id));

        return TeacherMapper.toDTO(teacher);
    }

    public TeacherDTO save(CreateTeacherRequest request) {

        Teacher teacher = Teacher.builder()
                .firstName(request.getFirstName())
                .students(new ArrayList<>())
                .build();

        Teacher save = teacherRepository.save(teacher);

        return TeacherMapper.toDTO(save);
    }

    public TeacherDTO update(Long id, Map<String, Object> updates) {

        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException(id));

        updates.forEach((key, value) -> {
            switch (key) {
                case "name":
                    teacher.setFirstName((String) value);
                    break;
                default:
                    throw new IllegalArgumentException("Field " + key + " is not updatable");
            }
        });

        Teacher savedTeacher = teacherRepository.save(teacher);

        return TeacherMapper.toDTO(savedTeacher);
    }

    public void deleteById(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                        .orElseThrow(() -> new TeacherNotFoundException(id));
        teacher.getStudents().forEach(student -> student.setTeacher(null));
        teacherRepository.deleteById(id);
    }

}
