package pl.maropce.etutor.teacher;

import jakarta.persistence.*;
import lombok.*;
import pl.maropce.etutor.student.Student;
import pl.maropce.etutor.user.AppUserDetails;
import pl.maropce.etutor.user.AppUserDetails;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_details_id", nullable = false)
    private AppUserDetails appUserDetails;

    @OneToMany(mappedBy = "teacher", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Student> students = new ArrayList<>();
}
