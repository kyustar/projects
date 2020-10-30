package com.ssafy.happyhouse.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.happyhouse.auth.dao.AuthDaoImpl;
import com.ssafy.happyhouse.auth.dto.MemberUser;
@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private AuthDaoImpl authDao;
	
	@Override
	public String authenticateUser(MemberUser signInRequest) {
		return authDao.authenticateUser(signInRequest);
	}

	@Override
	@Transactional
	public boolean registerUser(MemberUser signUpRequest) {
		return authDao.registerUser(signUpRequest);
	}

	@Override
	public MemberUser getUser(String username) {
		return authDao.getUser(username);
	}

	@Override
	@Transactional
	public boolean modifyUserInfo(MemberUser chgInfoRequest) {
		return authDao.modifyUserInfo(chgInfoRequest);
	}
	
	@Override
	@Transactional
	public boolean modifyPassword(MemberUser chgPwRequest) {
		return authDao.modifyPassword(chgPwRequest);
	}
	
	@Override
	@Transactional
	public boolean removeUser(String username) {
		return authDao.removeUser(username);
	}
}

