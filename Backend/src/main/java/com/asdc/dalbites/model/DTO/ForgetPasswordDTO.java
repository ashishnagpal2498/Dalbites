package com.asdc.dalbites.model.DTO;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for handling forget password requests.
 */
@Getter
@Setter
public class ForgetPasswordDTO {
    public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	/**
	 * Gets the new password for the user account.
	 *
	 * @return The new password.
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * Sets the new password for the user account.
	 *
	 * @param password The new password.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	private String username;
    private String otp;
    private String password;
}
