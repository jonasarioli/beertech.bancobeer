package com.beertech.banco.infrastructure.repository.mysql;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.beertech.banco.infrastructure.repository.mysql.model.MySqlProfile;

public interface ProfileRepository extends JpaRepository<MySqlProfile, Long> {
    Optional<MySqlProfile> findByName(String name);

}
