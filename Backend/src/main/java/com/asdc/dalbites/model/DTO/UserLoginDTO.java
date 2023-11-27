package com.asdc.dalbites.model.DTO;

/**
 * Data Transfer Object (DTO) for representing user login information.
 */
public class UserLoginDTO {
    private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
