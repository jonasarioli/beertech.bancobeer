package com.beertech.banco.infrastructure.repository.mysql.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.security.core.GrantedAuthority;

import com.beertech.banco.domain.model.Profile;

@Entity
@Table(name = "profile",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name")
        })
public class MySqlProfile implements GrantedAuthority {

    private static final long serialVersionUID = 6280340062208576621L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public MySqlProfile() {
    }

    public MySqlProfile(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return this.name;
    }

    public MySqlProfile fromDomain(Profile profile) {
        return new MySqlProfile(profile.getId(), profile.getName());
    }

    public Profile toDomain(MySqlProfile profile) {
        return new Profile(profile.getId(), profile.getName());
    }


}
