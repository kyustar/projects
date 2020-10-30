package com.ssafy.happyhouse.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.auth.dto.MemberUser;
import com.ssafy.happyhouse.auth.help.ApiResult;
import com.ssafy.happyhouse.auth.help.JwtAuthenticationResult;
import com.ssafy.happyhouse.auth.service.AuthService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthService authService;

	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public ResponseEntity<?> authenticateUser(@RequestBody MemberUser signInRequest) {
		String jwt = authService.authenticateUser(signInRequest);

		return ResponseEntity.ok(new JwtAuthenticationResult(jwt));
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<?> registerUser(@RequestBody MemberUser signUpRequest) {
		boolean isS = authService.registerUser(signUpRequest);
		if (isS) {
			return ResponseEntity.ok(new ApiResult(true, "성공적으로 등록되었습니다."));
		} else {
			return ResponseEntity.ok(new ApiResult(false, "등록에 실패했습니다."));
		}
	}

	@RequestMapping(value = "/signout", method = RequestMethod.GET)
	public ResponseEntity<?> signOut() {
		return ResponseEntity.ok(new ApiResult(true, "로그아웃 되었습니다."));
	}

	@RequestMapping(value = "/users/{username}", method = RequestMethod.GET)
	public MemberUser getUserProfile(@PathVariable(value = "username") String username) {
		return authService.getUser(username);
	}

	@RequestMapping(value = "/users/userinfo", method = RequestMethod.POST)
	public ResponseEntity<?> modifyUserInfo(@RequestBody MemberUser chgInfoRequest) {
		boolean flag = authService.modifyUserInfo(chgInfoRequest);
		if (flag) {
			return ResponseEntity.ok(new ApiResult(true, "회원정보가 성공적으로 변경되었습니다."));
		} else {
			return ResponseEntity.ok(new ApiResult(false, "알 수 없는 오류가 발생했습니다."));
		}
	}

	@RequestMapping(value = "/users/password", method = RequestMethod.POST)
	public ResponseEntity<?> modifyPassword(@RequestBody MemberUser chgPwRequest) {
		boolean flag = authService.modifyUserInfo(chgPwRequest);
		if (flag) {
			return ResponseEntity.ok(new ApiResult(true, "비밀번호가 성공적으로 변경되었습니다."));
		} else {
			return ResponseEntity.ok(new ApiResult(false, "알 수 없는 오류가 발생했습니다."));
		}
	}

	@RequestMapping(value = "/users/{username}", method = RequestMethod.DELETE)
	public ResponseEntity<?> removeUser(@PathVariable(value = "username") String username) {
		boolean flag = authService.removeUser(username);
		if (flag) {
			return ResponseEntity.ok(new ApiResult(true, "성공적으로 탈퇴되었습니다."));
		} else {
			return ResponseEntity.ok(new ApiResult(false, "알 수 없는 오류가 발생했습니다."));
		}
	}

}
