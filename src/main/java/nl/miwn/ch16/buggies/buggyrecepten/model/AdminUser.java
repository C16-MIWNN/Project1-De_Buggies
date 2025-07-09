package nl.miwn.ch16.buggies.buggyrecepten.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

/**
*@author Marnix Ripke
* This program describes the admin of the application
*/

@Entity
@DiscriminatorValue("ADMIN")
public class AdminUser extends User{

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

}