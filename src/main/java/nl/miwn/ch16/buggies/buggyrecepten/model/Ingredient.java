package nl.miwn.ch16.buggies.buggyrecepten.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/*
 * @Author: Joost Numan
 * This program describes an ingredient that can be used in recipes
 */

@Entity
public class Ingredient {

    @Id @GeneratedValue
    private Long designId;

    private String name;
    private Double quantity;
    private String unit;

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

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
