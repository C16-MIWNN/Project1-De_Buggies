package nl.miwn.ch16.buggies.buggyrecepten.repositories;

import nl.miwn.ch16.buggies.buggyrecepten.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Marnix Ripke
 */

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByName(String name);

}
