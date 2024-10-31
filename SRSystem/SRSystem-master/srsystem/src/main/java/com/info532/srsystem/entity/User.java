package com.info532.srsystem.entity;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "SRSUser")
@SequenceGenerator(name = "user_seq", sequenceName = "USER_SEQ", allocationSize = 1)
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @Column(name = "ID")
    private Long id;

    @Column(name = "FIRSTNAME")
    private String firstName;

    @Column(name ="LASTNAME")
    private String lastName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Return a collection of authorities/roles for the user
        // For example, you can return a list of SimpleGrantedAuthority objects
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        // Return the username (email in this case) of the user
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        // Return true if the account is not expired, false otherwise
        return true; // Assuming accounts do not expire
    }

    @Override
    public boolean isAccountNonLocked() {
        // Return true if the account is not locked, false otherwise
        return true; // Assuming accounts are not locked
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Return true if the credentials are not expired, false otherwise
        return true; // Assuming credentials do not expire
    }

    @Override
    public boolean isEnabled() {
        // Return true if the user is enabled, false otherwise
        return true; // Assuming user is always enabled
    }

}
