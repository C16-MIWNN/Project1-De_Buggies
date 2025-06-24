package nl.miwn.ch16.buggies.buggyrecepten.model;

/*
 * @Author: Joost Numan
 * zet hier wat het programma doet
 */

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Ingredient {

    @Id @GeneratedValue
    private Long designId;

    private String name;
    private Double hoeveelheid;
    private String eenheid;

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

    public Double getHoeveelheid() {
        return hoeveelheid;
    }

    public void setHoeveelheid(Double hoeveelheid) {
        this.hoeveelheid = hoeveelheid;
    }

    public String getEenheid() {
        return eenheid;
    }

    public void setEenheid(String eenheid) {
        this.eenheid = eenheid;
    }
}
