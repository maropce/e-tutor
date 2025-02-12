package pl.maropce.etutor.user;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String firstName;
    private List<String> roles;


}
