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
	public String kakoCallback(String code) {//Date�� �������ִ� ��Ʈ�ѷ� �Լ�
		
		RestTemplate rt = new RestTemplate();
		
		//Http ��� ������Ʈ ���� 
		HttpHeaders headers = new HttpHeaders();
		headers.add("content-type","application/x-www-form-urlencoded;charset=utf-8");
		
		//HttpBody ������Ʈ ����
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "8497efaac60af87f748a1db180e673aa");
		params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
		params.add("code", code);
		
		//����� �ٵ� �ϳ��� ������Ʈ�� ���
		HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest = 
				new HttpEntity<>(params, headers); //�ٵ������� ������� �����ִ� ��ƼƼ�� ��
		
		//exchange��� �޼ҵ尡 HttpEntity��� ������Ʈ�� �Ű������� ���޹���
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
		
		// �ѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤ�
		System.out.println(oauthToken.getAccess_token());
		
		RestTemplate rt2 = new RestTemplate();
		
		//Http ��� ������Ʈ ����
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("content-type","application/x-www-form-urlencoded;charset=utf-8");
		headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token());
		
		//����� �ٵ� �ϳ��� ������Ʈ�� ���
		HttpEntity<MultiValueMap<String,String>> kakaoProfileRequest = 
				new HttpEntity<>(headers2); //�ٵ������� ������� �����ִ� ��ƼƼ�� ��
		
		//exchange��� �޼ҵ尡 HttpEntity��� ������Ʈ�� �Ű������� ���޹���
		ResponseEntity<String> response2 = rt2.exchange(
				"https://kapi.kakao.com/v2/user/me",
				HttpMethod.POST,
				kakaoProfileRequest,
				String.class);
		
		System.out.println(response2.getBody());
		
		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		
		try {								//���� JSON��ü�� KakaoProfile Ŭ������ Java��ü�� ����
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		System.out.println("īī�� ���̵�(��ȣ) : " + kakaoProfile.getId());
		System.out.println("īī�� �̸��� : " + kakaoProfile.getKakao_account().getEmail());
		
		System.out.println("��α׼��� �������� : "+ kakaoProfile.getKakao_account().getEmail()+"_blog");
		System.out.println("��α׼��� �̸��� : "+kakaoProfile.getKakao_account().getEmail());
		System.out.println("��α׼��� �н����� : "+cosKey);
		UUID GarbagePassword = UUID.randomUUID();
		
		User kakaoUser = User.builder()
				.username(kakaoProfile.getId()+"_")
				.password(cosKey)
				.email(kakaoProfile.getKakao_account().getEmail())
				.build();
		System.out.println("111");
		//�̹� ������ Ȥ�� ������ üũ�ؼ� ó��
		User originUser = userService.ȸ��ã��(kakaoUser.getUsername());
		System.out.println("2222");
		if(originUser.getUsername() == null) {
			System.out.println("���� ȸ���� �ƴմϴ�!");
			userService.ȸ������(kakaoUser);
		}
		System.out.println("33333333333333");
		System.out.println("�ڵ� �α����� �����մϴ�.");
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), cosKey));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		System.out.println("4444444");
	
		
		return "redirect:/";
	}
}
