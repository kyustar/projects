package com.ssafy.happyhouse.auth.controller;

import javax.servlet.http.HttpSession;

//import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ssafy.happyhouse.auth.dto.AuthRequest;
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

	public static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	// http://localhost:8197/ssafyvueapi/auth/signin
	// http://localhost:8397/api/auth/signin
	// {"usernameOrEmail":"aaaaaa", "password":"aaaaaa"}
	/*
	 * { "accessToken":
	 * "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyMSIsImlhdCI6MTU5MjA2MjU4MSwiZXhwIjoxNTkyNjY3MzgxfQ.MFkfOHmyWecDBM_UCpIUGsp2ZfWhyek5aqs0MtRNidJbnmLhDf80GyMI2bH3Ndu4ozhDIJKlpimjltO6iGfKqg",
	 * "tokenType": "Bearer" }
	 */
	// @ApiOperation(value = " 로그인", response = JwtAuthenticationResult.class)
	/*
	 * @RequestMapping(value = "/signin", method = RequestMethod.POST) public
	 * ResponseEntity<?> authenticateUser(HttpSession session, @RequestBody
	 * LoginRequest loginRequest) {
	 * 
	 * logger.info("1-------------authenticateUser-----------------------------"+
	 * loginRequest);
	 * 
	 * 
	 * String jwt=authService.authenticateUser(new
	 * MemberUser(loginRequest.getUsernameOrEmail(), "",
	 * loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));
	 * logger.info("2-------------authenticateUser-----------------------------"+jwt
	 * ); if(jwt != "") { se session.setAttribute("accessToken", jwt);
	 * session.setAttribute("usernameoremail", loginRequest.getUsernameOrEmail()); }
	 * return ResponseEntity.ok(new JwtAuthenticationResult(jwt)); }
	 */

//	@ApiOperation(value = " 로그인", response = JwtAuthenticationResult.class)
	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public ResponseEntity<?> authenticateUser(HttpSession session, @RequestBody MemberUser signinRequest) {
		String jwt=authService.authenticateUser(signinRequest);
//		if(jwt != "") {
//			session.setAttribute(signinRequest.getUsername(), jwt);
//			System.out.println(signinRequest.getUsername());
//			System.out.println(jwt);
//		}
		return ResponseEntity.ok(new JwtAuthenticationResult(jwt));
	}

	/*
	 * http://localhost:8197/ssafyvue/api/auth/signup select memberuser0_.id as
	 * id1_2_, memberuser0_.created_at as created_2_2_, memberuser0_.updated_at as
	 * updated_3_2_, memberuser0_.email as email4_2_, memberuser0_.name as name5_2_,
	 * memberuser0_.password as password6_2_, memberuser0_.username as username7_2_
	 * from users memberuser0_ where memberuser0_.username=? or memberuser0_.email=?
	 * select roles0_.user_id as user_id1_1_0_, roles0_.role_id as role_id2_1_0_,
	 * role1_.id as id1_0_1_, role1_.name as name2_0_1_ from user_roles roles0_
	 * inner join roles role1_ on roles0_.role_id=role1_.id where roles0_.user_id=?
	 * http://localhost:8397/api/auth/signup {"name":"vvvvvv", "username":"vvvvvv",
	 * "email":"vvvvvv@naver.com","password":"vvvvvv" } {"success":"true",
	 * "message":"성공적으로 등록되었습니다."}
	 */
	// @ApiOperation(value = " 회원가입", response = ApiResult.class)
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<?> registerUser(@RequestBody MemberUser signUpRequest) {
//		logger.info("1-------------registerUser-----------------------------" + signUpRequest);

		boolean isS = authService.registerUser(signUpRequest);
		if (isS) {
			return ResponseEntity.ok(new ApiResult(true, "성공적으로 등록되었습니다."));
		} else {
			return ResponseEntity.ok(new ApiResult(false, "등록에 실패했습니다."));
		}

	}

	@RequestMapping(value = "/signout", method = RequestMethod.GET)
	public ResponseEntity<?> signOut(HttpSession session) {
		session.invalidate();
		return ResponseEntity.ok(new ApiResult(true, "로그아웃 되었습니다."));
	}

//	@GetMapping("/users/{username}")
//    public MemberUser getUserProfile(@PathVariable(value = "username") String username) {
//        return authService.getUser(username);
//    }
	
	@PostMapping("/users")
    public MemberUser getUserProfile(HttpSession session, @RequestBody AuthRequest authRequest) {
//		String accessToken = (String) session.getAttribute(authRequest.getUsername());
//		if(accessToken != null && accessToken.equals(authRequest.getAccessToken())) {
			return authService.getUser(authRequest.getUsername());
//		} else {
//			return new MemberUser();
//		}
    }
	
	@PostMapping("/users/userinfo")
    public ResponseEntity<?> modifyUserInfo(HttpSession session, @RequestBody AuthRequest authRequest) {
//		String accessToken = (String) session.getAttribute(authRequest.getUsername());		
//		if(accessToken != null && accessToken.equals(authRequest.getAccessToken())) {
			boolean flag = authService.modifyUserInfo(authRequest);
			if(flag) {
				return ResponseEntity.ok(new ApiResult(true, "성공적으로 변경되었습니다."));
			} else {
				return ResponseEntity.ok(new ApiResult(false, "알 수 없는 오류가 발생했습니다."));
			}
//		} else {
//			return ResponseEntity.ok(new ApiResult(false, "세션이 만료되었습니다. 다시 로그인해주세요"));
//		}
    }
	
	@PostMapping("/users/password")
    public ResponseEntity<?> modifyPassword(HttpSession session, @RequestBody AuthRequest authRequest) {
//		String accessToken = (String) session.getAttribute(authRequest.getUsername());
//		if(accessToken != null && accessToken.equals(authRequest.getAccessToken())) {
			boolean flag = authService.modifyUserInfo(authRequest);
			if(flag) {
				return ResponseEntity.ok(new ApiResult(true, "성공적으로 변경되었습니다."));
			} else {
				return ResponseEntity.ok(new ApiResult(false, "알 수 없는 오류가 발생했습니다."));
			}
//		} else {
//			return ResponseEntity.ok(new ApiResult(false, "세션이 만료되었습니다. 다시 로그인해주세요"));
//		}
    }

	@PostMapping("/users/delete")
	public ResponseEntity<?> removeUser(HttpSession session, @RequestBody AuthRequest authRequest) {
//		String accessToken = (String) session.getAttribute(authRequest.getUsername());
//		if(accessToken != null && accessToken.equals(authRequest.getAccessToken())) {
			boolean flag = authService.removeUser(authRequest.getUsername());
			if(flag) {
				return ResponseEntity.ok(new ApiResult(true, "성공적으로 탈퇴되었습니다."));
			} else {
				return ResponseEntity.ok(new ApiResult(false, "알 수 없는 오류가 발생했습니다."));
			}
//		} else {
//			return ResponseEntity.ok(new ApiResult(false, "세션이 만료되었습니다. 다시 로그인해주세요"));
//		}
	}
   	
}
