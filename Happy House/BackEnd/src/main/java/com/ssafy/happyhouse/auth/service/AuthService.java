package com.ssafy.happyhouse.auth.service;

import com.ssafy.happyhouse.auth.dto.MemberUser;

public interface AuthService {

	public String authenticateUser(MemberUser signInRequest);

	public boolean registerUser(MemberUser signUpRequest);

	public MemberUser getUser(String username);

	public boolean modifyUserInfo(MemberUser chgInfoRequest);

	public boolean modifyPassword(MemberUser chgPwRequest);

	public boolean removeUser(String username);
}
