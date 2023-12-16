package com.ssafy.user.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.user.model.UserDto;
import com.ssafy.user.model.service.UserService;
import com.ssafy.util.JWTUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Slf4j
@Api(value = "/user", tags= {"UserAPI"}, description="유저에 관한 RESTfulAPI")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private JWTUtil jwtUtil;
	
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@Value("${email.sender.id}")
	private String email_sender_id;
	@Value("${email.sender.pw}")
	private String email_sender_pw;
	
	// 회원가입
	@ApiOperation(value = "회원가입", notes = "유저정보를 입력하여 회원가입 처리")
	@PostMapping(value="/join")
	public ResponseEntity<?> join(@RequestBody UserDto userDto) {
		log.debug("userJoin userDto : {}", userDto);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			userService.joinUser(userDto);
			resultMap.put("success", true);
			status = HttpStatus.CREATED;
			
		} catch (Exception e) {
			log.debug("회원가입 에러 발생 : {}", e);
			resultMap.put("success", false);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	// id 체크
	@GetMapping(value="/idcheck/{user_id}")
	public ResponseEntity<?> idcheck(@PathVariable("user_id") String user_id) {
		log.debug("idCheck userid : {}", user_id);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			int cnt = userService.idCheck(user_id);
			status = HttpStatus.OK;
			if(cnt==0) {
				resultMap.put("usable", true);
			} else if(cnt>0) {
				resultMap.put("usable", false);
			}
		} catch (Exception e) {
			log.debug("아이디 체크 에러 발생 : {}", e);
			resultMap.put("message", "아이디 체크 에러");
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	// 로그인
	@ApiOperation(value = "로그인", notes = "아이디와 비밀번호를 이용하여 로그인 처리.")
	@PostMapping
	public ResponseEntity<?> login(@RequestBody UserDto userDto) {
		log.debug("userLogin userDto : {}", userDto);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			UserDto resultUserDto = userService.loginUser(userDto);
			if(resultUserDto != null) {
				String accessToken = jwtUtil.createAccessToken(resultUserDto.getUser_id());
				String refreshToken = jwtUtil.createRefreshToken(resultUserDto.getUser_id());
				log.debug("access token : {}", accessToken);
				log.debug("refresh token : {}", refreshToken);
				
				//발급받은 refresh token을 DB에 저장.
				userService.saveRefreshToken(resultUserDto.getUser_id(), refreshToken);
				
				resultMap.put("user_id", resultUserDto.getUser_id());
				resultMap.put("user_name", resultUserDto.getUser_name());
				resultMap.put("access_token", accessToken);
				resultMap.put("refresh_token", refreshToken);
				
				
				status = HttpStatus.CREATED;
			}
			else {
				resultMap.put("message", "아이디 또는 패스워드를 확인해주세요.");
				status = HttpStatus.UNAUTHORIZED;
			}
		} catch (Exception e) {
			log.debug("로그인 에러 발생 : {}", e);
			resultMap.put("message", "로그인 에러");
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	// 로그아웃
	@ApiOperation(value = "로그아웃", notes = "유효한 토큰을 주면 로그아웃 처리.")
	@GetMapping(value = "/logout")
	public ResponseEntity<?> logout(HttpServletRequest request) {
		log.debug("userLogout");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String accessToken = request.getHeader("Authorization");
		if (jwtUtil.checkToken(accessToken)) {
			String accessUserId=jwtUtil.getUserId(accessToken);
			log.debug(" 로그아웃 아이디 : {}", accessUserId);
			try {
				userService.deleteRefreshToken(accessUserId);
				resultMap.put("message", "로그아웃이 완료되었습니다.");
				status = HttpStatus.OK;
			} catch (Exception e) {
				log.error("정보조회 실패 : {}", e);
				resultMap.put("message", "사용자 정보 조회 실패");
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			log.debug("사용 불가능한 토큰");
			resultMap.put("message", "유효하지 않은 토큰입니다.");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	// 유저정보조회
	@GetMapping
	public ResponseEntity<?> userInfo(HttpServletRequest request) {
		String accessToken = request.getHeader("Authorization");
		log.debug("userInfo token :  {}", accessToken);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		
		if (jwtUtil.checkToken(accessToken)) {
			log.debug("사용 가능한 토큰");
			try {
				UserDto userDto = userService.userInfo(jwtUtil.getUserId(accessToken));
				resultMap.put("userInfo", userDto);
				status = HttpStatus.OK;
			} catch (Exception e) {
				log.error("정보조회 실패 : {}", e);
				resultMap.put("message", "사용자 정보 조회 실패");
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			log.debug("사용 불가능한 토큰");
			resultMap.put("message", "사용자 인증 정보가 유효하지 않습니다.");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	// 유저정보 수정 비밀번호 확인
	@PostMapping("/check")
	public ResponseEntity<?> userCheck(@RequestBody UserDto userDto, HttpServletRequest request) {
		String accessToken = request.getHeader("Authorization");
		log.debug("userCheck UserDto :  {}", userDto);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		
		if(jwtUtil.checkToken(accessToken)) {
			log.debug("사용 가능한 토큰");
			
			try {
				int count = userService.userCheck(jwtUtil.getUserId(accessToken), userDto);
				status = HttpStatus.OK;
				if(count == 0 ) {
					resultMap.put("success", false);
					resultMap.put("message", "사용자 인증 실패(아이디, 비밀번호확인)");
				} else if(count > 0 ) {
					resultMap.put("success", true);
					resultMap.put("message", "사용자 인증 완료");
				}
			} catch (Exception e) {
				log.error("정보조회 실패 : {}", e);
				resultMap.put("message", "사용자 정보 조회 실패");
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			log.debug("사용 불가능한 토큰");
			resultMap.put("message", "사용자 인증 정보가 유효하지 않습니다.");
			status = HttpStatus.UNAUTHORIZED;
		}
		
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	// 유저정보수정
	@PutMapping
	public ResponseEntity<?> userUpdate(@RequestBody UserDto userDto, HttpServletRequest request) {
		String accessToken = request.getHeader("Authorization");
		log.debug("userUpdate UserDto :  {}", userDto);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		
		if (jwtUtil.checkToken(accessToken)) {
			log.debug("사용 가능한 토큰");
			try {
				int updateNum = userService.userInfoUpdate(jwtUtil.getUserId(accessToken), userDto);
				if(updateNum>0) resultMap.put("updateSuccess", true);
				else if(updateNum==0) resultMap.put("updateSuccess", false);
				status = HttpStatus.OK;
			} catch (Exception e) {
				log.error("정보조회 실패 : {}", e);
				resultMap.put("message", "사용자 정보 조회 실패");
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			log.debug("사용 불가능한 토큰");
			resultMap.put("message", "사용자 인증 정보가 유효하지 않습니다.");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	// 회원 탈퇴
	@DeleteMapping
	public ResponseEntity<?> AccountCancellation(HttpServletRequest request) {
		String accessToken = request.getHeader("Authorization");
		log.debug("AccountCancellation");
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		
		if (jwtUtil.checkToken(accessToken)) {
			log.debug("사용 가능한 토큰");
			try {
				int CancellationNum = userService.AccountCancellation(jwtUtil.getUserId(accessToken));
				if(CancellationNum>0) resultMap.put("Success", true);
				else if(CancellationNum==0) resultMap.put("Success", false); 
				status = HttpStatus.OK;
			} catch (Exception e) {
				log.error("정보조회 실패 : {}", e);
				resultMap.put("message", "사용자 정보 조회 실패");
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			log.debug("사용 불가능한 토큰");
			resultMap.put("message", "사용자 인증 정보가 유효하지 않습니다.");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	// 아이디 찾기
	@PostMapping("/findid")
	public ResponseEntity<?> FindUserId(@RequestBody UserDto userDto) {
		log.debug("FindId userDto : {}", userDto);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			UserDto resultUserDto = userService.FindUserId(userDto);
			resultMap.put("user_id", resultUserDto.getUser_id());
			status = HttpStatus.OK;
		} catch (Exception e) {
			resultMap.put("message", "입력하신 정보가 일치하지 않습니다.");
			status = HttpStatus.OK;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	// 비밀번호 찾기
	@PostMapping("/findpw")
	public ResponseEntity<?> FindUserPw(@RequestBody UserDto userDto) {
		log.debug("FindPw userDto : {}", userDto);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			UserDto resultUserDto = userService.FindUserPw(userDto);
			if(resultUserDto==null) {
				log.debug("입력하신 정보가 일치하지 않습니다.");
				resultMap.put("message", "입력하신 정보가 일치하지 않습니다.");
				status = HttpStatus.OK;
				return new ResponseEntity<Map<String, Object>>(resultMap, status);
			}
			System.out.println(resultUserDto);
			//////////////////////
			Properties prop = new Properties();
			prop.put("mail.smtp.host", "smtp.gmail.com");
			prop.put("mail.smtp.port", 465);
			prop.put("mail.smtp.auth", "true");
			prop.put("mail.smtp.ssl.enable", "true");
			prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
			Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(email_sender_id, email_sender_pw);
				}
			});
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email_sender_id));
            //수신자메일주소
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(resultUserDto.getEmail_id()+ "@"+resultUserDto.getEmail_domain())); 
            // Subject
            message.setSubject("voyage 비밀번호 찾기"); //메일 제목을 입력
            // Text
            message.setText(resultUserDto.getUser_name() + "님의 비밀번호는 " + resultUserDto.getUser_password() + "입니다.");    //메일 내용을 입력
            // send the message
            Transport.send(message); ////전송
            resultMap.put("message", "이메일 발송 성공");
			//////////////////////
		} catch (Exception e) {
			log.debug("이메일 발송 도중 오류 발생. : {}", e);
			resultMap.put("message", "이메일 발송 도중 오류가 발생하였습니다.");
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	// 새로운 토큰
	@ApiOperation(value = "Access Token 재발급", notes = "만료된 access token을 재발급받는다.", response = Map.class)
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody UserDto userDto, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String token = request.getHeader("refreshToken");
		log.debug("token : {}, userDto : {}", token, userDto);
		if (jwtUtil.checkToken(token)) {
			try {
				if (token.equals(userService.getRefreshToken(userDto.getUser_id()))) {
					String accessToken = jwtUtil.createAccessToken(userDto.getUser_id());
					log.debug("token : {}", accessToken);
					log.debug("정상적으로 액세스토큰 재발급!!!");
					resultMap.put("access_token", accessToken);
					status = HttpStatus.CREATED;
				}
			} catch (Exception e) {
				log.error("DB 정보조회 실패 : {}", e);
				resultMap.put("message", "DB 정보 조회 실패");
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			log.debug("리프레쉬토큰도 사용불가!!!!!!!");
			resultMap.put("message", "유효하지 않은 토큰입니다.");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

}
