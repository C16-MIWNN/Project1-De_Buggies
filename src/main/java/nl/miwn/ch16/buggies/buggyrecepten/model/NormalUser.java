package nl.miwn.ch16.buggies.buggyrecepten.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * @author Marnix Ripke
 */

@Entity
@DiscriminatorValue("NORMAL")
public class NormalUser extends User {

}