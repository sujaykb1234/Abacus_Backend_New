package com.abacus.franchise.viewModels;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {

	private String username;
	private String password;
	private String rolename;
	private UUID userId;

}
