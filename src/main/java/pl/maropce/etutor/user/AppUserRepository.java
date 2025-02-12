package pl.maropce.etutor.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUserDetails, Long> {
    Optional<AppUserDetails> findByUsername(String username);
}
