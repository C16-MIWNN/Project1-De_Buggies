package nl.miwn.ch16.buggies.buggyrecepten.repositories;

import nl.miwn.ch16.buggies.buggyrecepten.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
