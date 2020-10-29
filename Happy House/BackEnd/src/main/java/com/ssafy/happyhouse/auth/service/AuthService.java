package com.ssafy.happyhouse.auth.service;

import com.ssafy.happyhouse.auth.dto.MemberUser;

public interface AuthService {

	public String authenticateUser(MemberUser memberUser);

	public boolean registerUser(MemberUser signUpRequest);
	public MemberUser getUser(String username);
	
	public boolean modifyUserInfo(MemberUser modifyRequest);
	public boolean modifyPassword(MemberUser modifyRequest);
	public boolean removeUser(String username);
}
