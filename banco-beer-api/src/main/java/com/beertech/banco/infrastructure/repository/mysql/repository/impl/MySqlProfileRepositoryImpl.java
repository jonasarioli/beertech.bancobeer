package com.beertech.banco.infrastructure.repository.mysql.repository.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.beertech.banco.domain.Profile;
import com.beertech.banco.domain.repository.ProfileRepository;
import com.beertech.banco.infrastructure.repository.mysql.model.MySqlProfile;

@Repository
public class MySqlProfileRepositoryImpl implements ProfileRepository{

	private com.beertech.banco.infrastructure.repository.mysql.ProfileRepository profileRepository;

	  @Autowired
	  public MySqlProfileRepositoryImpl(
			  com.beertech.banco.infrastructure.repository.mysql.ProfileRepository profileRepository) {
	    this.profileRepository = profileRepository;
	  }
	
	@Override
	public Profile save(Profile profile) {
		return new MySqlProfile().toDomain((profileRepository.save(new MySqlProfile().fromDomain(profile))));
	}

	@Override
	public Optional<Profile> findByName(String name) {
		return profileRepository.findByName(name).map(new MySqlProfile()::toDomain);
	}

}
