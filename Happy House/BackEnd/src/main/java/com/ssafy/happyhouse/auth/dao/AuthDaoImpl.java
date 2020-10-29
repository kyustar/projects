package com.ssafy.happyhouse.auth.dao;

//import java.rmi.server.UID;
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

	public String authenticateUser(MemberUser memberUser) {
		MemberUser member = sqlSession.selectOne(ns + "authenticateUser", memberUser);

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
	
	public boolean modifyUserInfo(MemberUser modifyRequest) {
		MemberUser getUser = getUser(modifyRequest.getUsername());
		System.out.println(getUser);
		System.out.println(modifyRequest.getUsername());
		if (getUser != null && getUser.getUsername().equals(modifyRequest.getUsername())) {
			sqlSession.update(ns + "modifyUserInfo", modifyRequest);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean modifyPassword(MemberUser modifyRequest) {
		MemberUser getUser = getUser(modifyRequest.getUsername());
		
		if (getUser != null && getUser.getUsername().equals(modifyRequest.getUsername())) {
			sqlSession.update(ns + "modifyPassword", modifyRequest);
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
