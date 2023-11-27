package com.asdc.dalbites.model.DTO;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for representing account verification information.
 */
@Getter
@Setter
public class VerifyAccountDTO {
    public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	String otp;
}
