package com.beertech.banco.domain.service;

import java.util.Optional;

import com.beertech.banco.domain.Profile;

public interface ProfileService {

	Optional<Profile> findByName(String name);
	Profile save(Profile profile);
}
