package pl.maropce.etutor.student;

import org.springframework.stereotype.Service;
import pl.maropce.etutor.student.dto.StudentDTO;
import pl.maropce.etutor.student.dto.StudentMapper;
import pl.maropce.etutor.student.exception.StudentNotFoundException;

import java.util.List;
import java.util.Map;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<StudentDTO> findAll() {
        return studentRepository.findAll()
                .stream()
                .map(StudentMapper::toDTO)
                .toList();
    }

    public StudentDTO findById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));

        return StudentMapper.toDTO(student);
    }

    public StudentDTO save(StudentDTO student) {

        Student save = studentRepository.save(
                StudentMapper.toEntity(student));

        return StudentMapper.toDTO(save);
    }

    public StudentDTO update(Long id, Map<String, Object> updates) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));

        updates.forEach((key, value) -> {
            switch (key) {
                case "firstName":
                    student.setFirstName((String) value);
                    break;
                case "lastName":
                    student.setLastName((String) value);
                    break;
                case "email":
                    student.setEmail((String) value);
                    break;
                case "phone":
                    student.setPhone((String) value);
                    break;
                case "discord":
                    student.setDiscord((String) value);
                    break;
                case "about":
                    student.setAbout((String) value);
                    break;
                case "classType":
                    student.setClassType((String) value);
                    break;
                default:
                    throw new IllegalArgumentException("Field " + key + " is not updatable");
            }
        });

        Student save = studentRepository.save(student);

        return StudentMapper.toDTO(save);
    }

    public List<StudentDTO> findByKeyword(String keyword) {
        List<Student> students = studentRepository.findByKeyword(keyword);

        return students
                .stream()
                .map(StudentMapper::toDTO)
                .toList();
    }

    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }
}
