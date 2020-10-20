package com.beertech.banco.domain.service.impl;

import java.util.Optional;

import com.beertech.banco.domain.Profile;
import com.beertech.banco.domain.repository.ProfileRepository;
import com.beertech.banco.domain.service.ProfileService;

public class ProfileServiceImpl implements ProfileService {

	private final ProfileRepository profileRepository;
	
	public ProfileServiceImpl(ProfileRepository profileaRepository) {
		this.profileRepository = profileaRepository;
	}

	@Override
	public Optional<Profile> findByName(String name) {
		return profileRepository.findByName("ROLE_" + name);
	}

	@Override
	public Profile save(Profile profile) {
		return profileRepository.save(profile);
	}
}
