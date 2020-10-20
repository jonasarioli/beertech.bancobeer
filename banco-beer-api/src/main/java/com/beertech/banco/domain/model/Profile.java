package com.beertech.banco.domain.model;

public class Profile {

	private Long id;
	private String name;
	public Profile(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Profile(String name) {
		this.name = "ROLE_" + name;
	}
	
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
}
