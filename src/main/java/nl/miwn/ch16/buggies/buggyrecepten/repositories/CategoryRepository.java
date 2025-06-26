package nl.miwn.ch16.buggies.buggyrecepten.repositories;

import nl.miwn.ch16.buggies.buggyrecepten.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
