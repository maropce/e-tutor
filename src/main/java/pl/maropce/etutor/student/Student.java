package pl.maropce.etutor.student;

import jakarta.persistence.*;
import lombok.*;
import pl.maropce.etutor.lesson.Lesson;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String discord;
    private String about;
    private String classType;

//    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    private List<Lesson> lessons = new ArrayList<>();

}
