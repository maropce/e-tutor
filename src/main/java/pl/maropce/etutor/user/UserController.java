package pl.maropce.etutor.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @GetMapping("/user")
    public ResponseEntity<UserDTO> userDetails(Authentication auth) {


        if (auth == null || !auth.isAuthenticated()) {

            System.out.println("UNAUTHENTICATED!!!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        AppUserDetails principal = (AppUserDetails) auth.getPrincipal();

        String firstName = principal.getUsername();
        List<String> roles = principal.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();


        UserDTO user = new UserDTO(principal.getId(), firstName, roles);

        System.out.println(user.getFirstName());
        System.out.println(user.getRoles().toString());
        return ResponseEntity.ok(user);

    }
}
