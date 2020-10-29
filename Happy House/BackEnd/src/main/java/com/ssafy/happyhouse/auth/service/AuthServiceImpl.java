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
	public String authenticateUser(MemberUser memberUser) {
		return authDao.authenticateUser(memberUser);
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
	public boolean modifyUserInfo(MemberUser modifyRequest) {
		return authDao.modifyUserInfo(modifyRequest);
	}
	
	@Override
	@Transactional
	public boolean modifyPassword(MemberUser modifyRequest) {
		return authDao.modifyPassword(modifyRequest);
	}
	
	@Override
	@Transactional
	public boolean removeUser(String username) {
		return authDao.removeUser(username);
	}
}

