package nl.miwn.ch16.buggies.buggyrecepten.repositories;

import nl.miwn.ch16.buggies.buggyrecepten.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    Optional<Ingredient> findByName(String name);
}
