package com.ssafy.happyhouse.auth.dao;

import java.util.Date;
import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.happyhouse.auth.dto.MemberUser;

@Repository
public class AuthDaoImpl {

	private String ns = "com.ssafy.happyhouse.auth.dao.AuthDao.";

	@Autowired
	private SqlSession sqlSession;

	public String authenticateUser(MemberUser signInRequest) {
		MemberUser member = sqlSession.selectOne(ns + "authenticateUser", signInRequest);

		if (member == null || member.getUsername() == null) {
			return "";
		} else {
			String s = "" + (new Date().getTime()) + member.hashCode() + UUID.randomUUID();
			return s;
		}
	}

	public boolean registerUser(MemberUser signUpRequest) {
		MemberUser getUser = getUser(signUpRequest.getUsername());
		
		if (getUser == null || getUser.getUsername() == null) {
			sqlSession.insert(ns + "registerUser", signUpRequest);
			return true;
		} else {
			return false;
		}
	}

	public MemberUser getUser(String username) {
		return sqlSession.selectOne(ns + "getUser", username);
	}
	
	public boolean modifyUserInfo(MemberUser chgInfoRequest) {
		MemberUser getUser = getUser(chgInfoRequest.getUsername());

		if (getUser != null && getUser.getUsername().equals(chgInfoRequest.getUsername())) {
			sqlSession.update(ns + "modifyUserInfo", chgInfoRequest);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean modifyPassword(MemberUser chgPwRequest) {
		MemberUser getUser = getUser(chgPwRequest.getUsername());
		
		if (getUser != null && getUser.getUsername().equals(chgPwRequest.getUsername())) {
			sqlSession.update(ns + "modifyPassword", chgPwRequest);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean removeUser(String username) {
		MemberUser getUser = getUser(username);
		
		if(getUser != null && getUser.getUsername().equals(username)) {
			sqlSession.delete(ns + "removeUser", username);
			return true;
		} else {
			return false;
		}
	}

}
