package nl.miwn.ch16.buggies.buggyrecepten.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

/*
 * @Author: Joost Numan
 * zet hier wat het programma doet
 */

@Entity
public class Recept {

    @Id @GeneratedValue
    private Long designId;

    private String name;
    private List<String> stappen;

    @ManyToMany
    private Set<Ingredient> ingredienten;

    public Long getDesignId() {
        return designId;
    }

    public void setDesignId(Long designId) {
        this.designId = designId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getStappen() {
        return stappen;
    }

    public void setStappen(List<String> stappen) {
        this.stappen = stappen;
    }

    public Set<Ingredient> getIngredienten() {
        return ingredienten;
    }

    public void setIngredienten(Set<Ingredient> ingredienten) {
        this.ingredienten = ingredienten;
    }
}
