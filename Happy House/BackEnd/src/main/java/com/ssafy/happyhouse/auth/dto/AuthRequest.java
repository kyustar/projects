package com.ssafy.happyhouse.auth.dto;

public class AuthRequest extends MemberUser{

	private String accessToken;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
}
