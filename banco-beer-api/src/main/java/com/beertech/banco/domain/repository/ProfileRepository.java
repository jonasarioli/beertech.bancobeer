package com.beertech.banco.domain.repository;

import java.util.Optional;

import com.beertech.banco.domain.model.Profile;

public interface ProfileRepository {

	Profile save(Profile profile);
	Optional<Profile> findByName(String name);
}
