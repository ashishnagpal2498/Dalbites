package com.asdc.dalbites.model.DTO;

import lombok.Getter;
import lombok.Setter;

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
