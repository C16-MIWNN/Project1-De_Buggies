package nl.miwn.ch16.buggies.buggyrecepten.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.Set;

/**
*@author Marnix Ripke
* This program describes the admin of the application
*/

@Entity
@DiscriminatorValue("ADMIN")
public class AdminUser extends User{

    public AdminUser() {
        this.setRoles(Set.of("ROLE_ADMIN"));
    }

}