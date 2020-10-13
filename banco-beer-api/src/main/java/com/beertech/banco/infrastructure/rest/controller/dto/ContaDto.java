package com.beertech.banco.infrastructure.rest.controller.dto;

import javax.validation.constraints.NotBlank;

public class ContaDto {
	@NotBlank
	private String hash;

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}
	
}
