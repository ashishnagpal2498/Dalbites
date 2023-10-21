package com.asdc.dalbites.model.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpDTO {
    private String username;
	private String password;
    private String name;
    private String address;
    private String email;
    private String role;
    private int roleId;
}
