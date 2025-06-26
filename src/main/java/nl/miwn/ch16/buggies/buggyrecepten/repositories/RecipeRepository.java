package nl.miwn.ch16.buggies.buggyrecepten.repositories;


import nl.miwn.ch16.buggies.buggyrecepten.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Optional<Recipe> findByName(String name);

    List<Recipe> findAllByNameContains(String name);

}
