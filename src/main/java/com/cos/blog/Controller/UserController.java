package com.cos.blog.Controller;

import java.util.UUID;

import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Value;


@Controller
public class UserController {
	
	private String cosKey = "cos1234";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
		
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}
	
	@GetMapping("/user/updateForm")
	public String updateForm() {
		return "user/updateForm";
	}
	
	@GetMapping("auth/kakao/callback")
	public String kakoCallback(String code) {//Date를 리턴해주는 컨트롤러 함수
		
		RestTemplate rt = new RestTemplate();
		
		//Http 헤더 오브젝트 생성 
		HttpHeaders headers = new HttpHeaders();
		headers.add("content-type","application/x-www-form-urlencoded;charset=utf-8");
		
		//HttpBody 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "8497efaac60af87f748a1db180e673aa");
		params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
		params.add("code", code);
		
		//헤더와 바디를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest = 
				new HttpEntity<>(params, headers); //바디정보와 헤더값을 갖고있는 엔티티가 됌
		
		//exchange라는 메소드가 HttpEntity라는 오브젝트를 매개값으로 전달받음
		ResponseEntity<String> response = rt.exchange(
				"https://kauth.kakao.com/oauth/token",
				HttpMethod.POST,
				kakaoTokenRequest,
				String.class);
				
		ObjectMapper objectMapper = new ObjectMapper();
		
		OAuthToken oauthToken = null;
		
		try {
			oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		// ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		System.out.println(oauthToken.getAccess_token());
		
		RestTemplate rt2 = new RestTemplate();
		
		//Http 헤더 오브젝트 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("content-type","application/x-www-form-urlencoded;charset=utf-8");
		headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token());
		
		//헤더와 바디를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String,String>> kakaoProfileRequest = 
				new HttpEntity<>(headers2); //바디정보와 헤더값을 갖고있는 엔티티가 됌
		
		//exchange라는 메소드가 HttpEntity라는 오브젝트를 매개값으로 전달받음
		ResponseEntity<String> response2 = rt2.exchange(
				"https://kapi.kakao.com/v2/user/me",
				HttpMethod.POST,
				kakaoProfileRequest,
				String.class);
		
		System.out.println(response2.getBody());
		
		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		
		try {								//받은 JSON객체를 KakaoProfile 클래스에 Java객체로 저장
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		System.out.println("카카오 아이디(번호) : " + kakaoProfile.getId());
		System.out.println("카카오 이메일 : " + kakaoProfile.getKakao_account().getEmail());
		
		System.out.println("블로그서버 유저네임 : "+ kakaoProfile.getKakao_account().getEmail()+"_blog");
		System.out.println("블로그서버 이메일 : "+kakaoProfile.getKakao_account().getEmail());
		System.out.println("블로그서버 패스워드 : "+cosKey);
		UUID GarbagePassword = UUID.randomUUID();
		
		User kakaoUser = User.builder()
				.username(kakaoProfile.getId()+"_")
				.password(cosKey)
				.email(kakaoProfile.getKakao_account().getEmail())
				.build();
		System.out.println("111");
		//이미 가입자 혹은 비가입자 체크해서 처리
		User originUser = userService.회원찾기(kakaoUser.getUsername());
		System.out.println("2222");
		if(originUser.getUsername() == null) {
			System.out.println("기존 회원이 아닙니다!");
			userService.회원가입(kakaoUser);
		}
		System.out.println("33333333333333");
		System.out.println("자동 로그인을 진행합니다.");
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), cosKey));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		System.out.println("4444444");
	
		
		return "redirect:/";
	}
}
