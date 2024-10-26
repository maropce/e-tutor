package pl.maropce.etutor.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import pl.maropce.etutor.lesson.LessonService;
import pl.maropce.etutor.student.StudentService;
import pl.maropce.etutor.student.dto.StudentDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
@Getter
public class AppConfig {

    private final StudentService studentService;
    private final LessonService lessonService;

    @Value("${app.url}")
    private String applicationURL;

    public AppConfig(StudentService studentService, LessonService lessonService) {
        this.studentService = studentService;
        this.lessonService = lessonService;
    }

    private static final String[] FIRST_NAMES = {
            "Anna", "Jan", "Kasia", "Marek", "Ola", "Tomasz", "Karolina",
            "Piotr", "Zuzanna", "Michał", "Natalia", "Adam"
    };

    private static final String[] LAST_NAMES = {
            "Kowalski", "Nowak", "Wiśniewski", "Wójcik", "Kozłowski",
            "Jankowski", "Mazur", "Krawczyk", "Zając", "Pawlak", "Duda", "Kucharski"
    };

    private static final String[] ABOUT_DESCRIPTIONS = {
            "Zaangażowany w naukę programowania.",
            "Pasjonuje się nowymi technologiami.",
            "Interesuje się rozwojem osobistym.",
            "Uwielbia rozwiązywać problemy.",
            "Ceni sobie współpracę w grupie.",
            "Stawia na praktyczne podejście do nauki.",
            "Chce zostać ekspertem w swojej dziedzinie.",
            "Ma duże ambicje zawodowe.",
            "Pragnie tworzyć innowacyjne rozwiązania.",
            "Lubi wyzwania i nowe doświadczenia.",
            "Jest otwarty na naukę i rozwój.",
            "Ceni sobie dobrą atmosferę w zespole."
    };

    private static final String[] CLASS_TYPES = {
            "Java", "Spring Boot", "JavaScript", "Python", "C++",
            "Data Science", "Web Development", "Mobile Development", "DevOps", "Machine Learning", "UI/UX Design", "Game Development"
    };

    @PostConstruct
    public void saveStudents() {
        List<StudentDTO> students = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 12; i++) {
            StudentDTO student = StudentDTO.builder()
                    .id((long) (i + 1))
                    .firstName(FIRST_NAMES[random.nextInt(FIRST_NAMES.length)])
                    .lastName(LAST_NAMES[random.nextInt(LAST_NAMES.length)])
                    .email("student" + (i + 1) + "@example.com")
                    .phone("123-456-78" + (random.nextInt(10)))
                    .discord("discord" + (i + 1))
                    .about(ABOUT_DESCRIPTIONS[random.nextInt(ABOUT_DESCRIPTIONS.length)])
                    .classType(CLASS_TYPES[random.nextInt(CLASS_TYPES.length)])
                    .build();
            students.add(student);
        }

        for (StudentDTO student : students) {
            studentService.save(student);
        }
    }

    @PostConstruct
    public void addSampleLessons() {
        List<StudentDTO> students = studentService.findAll();
        Random random = new Random();
        LocalDateTime now = LocalDateTime.now();

        students.forEach(student -> {
            int numberOfLessons = random.nextInt(4) + 1;

            for (int i = 0; i < numberOfLessons; i++) {
                LocalDateTime start;
                LocalDateTime end;

                do {
                    int hourOffset = random.nextInt(8) + 13;  // Generates a random hour between 8:00 and 20:00
                    int minuteOffset = random.nextBoolean() ? 0 : 30;  // Randomly picks 0 or 30 minutes

                    start = now.plusDays(random.nextInt(30))
                            .withHour(hourOffset)
                            .withMinute(minuteOffset)
                            .withSecond(0)
                            .withNano(0);
                    int duration = random.nextInt(2) + 1;  // Randomly sets lesson duration to 1 or 2 hours
                    end = start.plusHours(duration);
                } while (lessonService.existsOverlappingLesson(start, end));

                lessonService.save(student.getId(), start, end);
            }
        });
    }
}
