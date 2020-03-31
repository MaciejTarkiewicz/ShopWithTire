package pl.tarkiewicz.springsecuritysimplefactorauth.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tarkiewicz.springsecuritysimplefactorauth.User.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

}
