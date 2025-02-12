package pl.maropce.etutor.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.maropce.etutor.lesson.Lesson;
import pl.maropce.etutor.lesson.LessonRepository;
import pl.maropce.etutor.lesson.LessonService;
import pl.maropce.etutor.statistics.MonthlyStatistics;
import pl.maropce.etutor.statistics.MonthlyStatisticsRepository;
import pl.maropce.etutor.statistics.MonthlyStatisticsService;
import pl.maropce.etutor.student.Student;
import pl.maropce.etutor.student.StudentRepository;
import pl.maropce.etutor.teacher.Teacher;
import pl.maropce.etutor.teacher.TeacherRepository;
import pl.maropce.etutor.user.AppUserDetails;
import pl.maropce.etutor.user.AppUserRepository;

import java.time.LocalDateTime;
import java.util.*;

@Configuration
@Getter
public class AppConfig {

    private final StudentRepository studentRepository;
    private final LessonRepository lessonRepository;
    private final LessonService lessonService;
    private final TeacherRepository teacherRepository;
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final MonthlyStatisticsService monthlyStatisticsService;
    private final MonthlyStatisticsRepository monthlyStatisticsRepository;

    @Value("${app.url}")
    private String applicationURL;

    public AppConfig(StudentRepository studentRepository, LessonRepository lessonRepository, LessonService lessonService, TeacherRepository teacherRepository, AppUserRepository appUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder, MonthlyStatisticsService monthlyStatisticsService, MonthlyStatisticsRepository monthlyStatisticsRepository) {
        this.studentRepository = studentRepository;
        this.lessonRepository = lessonRepository;
        this.lessonService = lessonService;
        this.teacherRepository = teacherRepository;
        this.appUserRepository = appUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.monthlyStatisticsService = monthlyStatisticsService;
        this.monthlyStatisticsRepository = monthlyStatisticsRepository;
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
    public void addStudentsWithLessons() {

        List<Student> students = saveStudents();
        saveTeachers(students);
        addSampleLessons(students);

        MonthlyStatistics statistics = monthlyStatisticsService.generateMonthlyStatistics();
        monthlyStatisticsRepository.save(statistics);

    }

    private List<Student> saveStudents() {
        List<Student> students = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 12; i++) {
            Student student = Student.builder()
                    .id((long) (i + 1))
                    .firstName(FIRST_NAMES[random.nextInt(FIRST_NAMES.length)])
                    .lastName(LAST_NAMES[random.nextInt(LAST_NAMES.length)])
                    .email("student" + (i + 1) + "@example.com")
                    .phone((random.nextInt(899)+100) +  " " + (random.nextInt(899)+100) + " " + (random.nextInt(899)+100))
                    .discord("discord" + (i + 1))
                    .about(ABOUT_DESCRIPTIONS[random.nextInt(ABOUT_DESCRIPTIONS.length)])
                    .classType(CLASS_TYPES[random.nextInt(CLASS_TYPES.length)])
                    .build();
            students.add(student);
            AppUserDetails appUserDetails = AppUserDetails.builder()
                    .username(student.getEmail())
                    .password(bCryptPasswordEncoder.encode(student.getEmail()))
                    .role("STUDENT")
                    .build();
            appUserRepository.save(appUserDetails);
            student.setAppUserDetails(appUserDetails);
        }

        return studentRepository.saveAll(students);
    }

    public void saveTeachers(List<Student> allStudents) {
        //allStudents = studentRepository.findAll();
        Random random = new Random();
        // 1. Tworzymy 5 przykładowych nauczycieli
        List<Teacher> teachers = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Teacher teacher = new Teacher();
            teacher.setFirstName(FIRST_NAMES[random.nextInt(FIRST_NAMES.length)]);

            // 2. Losujemy liczbę uczniów, którą przypiszemy do nauczyciela (3-6 uczniów)
            int numberOfStudents = new Random().nextInt(4) + 3; // losuje liczbę od 3 do 6
            Collections.shuffle(allStudents); // Tasujemy listę uczniów

            // 3. Przypisujemy losową liczbę uczniów
            List<Student> assignedStudents = allStudents.subList(0, numberOfStudents);

            AppUserDetails appUserDetails = AppUserDetails.builder()
                    .username(teacher.getFirstName())
                    .password(bCryptPasswordEncoder.encode(teacher.getFirstName()))
                    .role("TEACHER")
                    .build();
            teacher.setAppUserDetails(appUserDetails);

            //appUserRepository.save(appUserDetails);
            teacherRepository.save(teacher);

            for (Student student : assignedStudents) {
                student.setTeacher(teacher);
            }

            teacher.setStudents(assignedStudents);

            teachers.add(teacher);

            //teacherRepository.save(teacher);

        }
        teacherRepository.saveAll(teachers);
    }

    public void saveTeachers2(List<Student> allStudents) {
        Random random = new Random();

        // Tworzymy 5 przykładowych nauczycieli
        for (int i = 1; i <= 5; i++) {
            Teacher teacher = new Teacher();
            teacher.setFirstName(FIRST_NAMES[random.nextInt(FIRST_NAMES.length)]);

            // Losujemy liczbę uczniów (3-6 uczniów)
            int numberOfStudents = new Random().nextInt(4) + 3;
            Collections.shuffle(allStudents);

            // Przypisujemy losową liczbę uczniów
            List<Student> assignedStudents = new ArrayList<>(allStudents.subList(0, numberOfStudents));

            // Ustawiamy nauczyciela dla każdego ucznia
            for (Student student : assignedStudents) {
                student.setTeacher(teacher); // Przypisanie nauczyciela
            }

            teacher.setStudents(assignedStudents); // Dodanie uczniów do nauczyciela

            // Zapisujemy nauczyciela razem z przypisanymi uczniami
            teacherRepository.save(teacher);

        }
    }


    private void addSampleLessons(List<Student> students) {
        Random random = new Random();
        LocalDateTime now = LocalDateTime.now();

        students.forEach(student -> {
            int numberOfLessons = random.nextInt(4) + 1;

            for (int i = 0; i < numberOfLessons; i++) {
                LocalDateTime start;
                LocalDateTime end;


                    int hourOffset = random.nextInt(8) + 13;  // Generates a random hour between 8:00 and 20:00
                    int minuteOffset = random.nextBoolean() ? 0 : 30;  // Randomly picks 0 or 30 minutes

                    start = now.plusDays(random.nextInt(30))
                            .withHour(hourOffset)
                            .withMinute(minuteOffset)
                            .withSecond(0)
                            .withNano(0);
                    int duration = random.nextInt(2) + 1;  // Randomly sets lesson duration to 1 or 2 hours
                    end = start.plusHours(duration);
                do {
                    start = start.plusDays(1);
                    end = end.plusDays(1);
                } while (lessonService.existsOverlappingLesson(start, end));

                Lesson lesson = Lesson.builder()
                        .student(student)
                        .title(student.getFirstName() + " " + student.getLastName())
                        .startDateTime(start)
                        .endDateTime(end)
                        .build();

                lessonRepository.save(lesson);


            }

            studentRepository.save(student);
        });
    }


}
