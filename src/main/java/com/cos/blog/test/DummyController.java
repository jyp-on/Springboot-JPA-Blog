package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@RestController
public class DummyController {
	
	@Autowired// DI - 더미컨트롤러가 메모리에 생성시 같이 생성
	private UserRepository userRepository;
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch(EmptyResultDataAccessException e){
			return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
		}
		
		return "삭제 되었습니다. id" + id;
	}
	
	//password, email 변경
	//@RequestBody - json데이터를 요청 -> Java Object로 변환해서 받아줌
	@Transactional //함수종료시 자동 commit
	@PutMapping("/dummy/user/{id}") 
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) {
		System.out.println("id : "+id );
		System.out.println("password : "+requestUser.getPassword());
		System.out.println("email : " +requestUser.getEmail());
		//User객체에 해당 아이디값 컬럼을 불러와서 저장한 후에
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		//바꿀 값들만 user에 저장
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		//save함수는 id를 전달하지 않으면 insert를 해주고
		//save함수는 id를 전달하고 해당 id에 대한 데이터가 있으면 update를 해주고
		//save함수는 id를 전달하고 해당 id에 대한 데이터가 없으면 insert를 해줌.
		//userRepository.save(user);
	
		
		//더티 체킹
		//영속화한(findById) user 객체에 값이 변경되면 변경을 감지해서 db에 update를 해줌
		return user;
	}
	
	//http://localhost:8000/blog/dummy/users
	@GetMapping("/dummy/users")
	public List<User> list(){ 
		//findAll이 list<T>로 반환하는 메소드
		return userRepository.findAll();
	}
	
	//페이징기능 - 한페이지당 2건의 데이터를 리턴할 예정
	@GetMapping("/dummy/user") 
	  public List<User> pageList(@PageableDefault(size=2,sort="id",direction = Sort.Direction.DESC) Pageable pageable){ 
		Page<User> pagingUser = userRepository.findAll(pageable);
		//페이징으로 받아서
		if(pagingUser.isLast()) {
			System.out.println("마지막 페이지입니다!");
		}
		else {
			System.out.println("첫 페이지입니다!");
		}
		List<User> users = pagingUser.getContent();
		//List로 리턴해주는게 좋음.
		return users;
	}
	//{id} 주소로 파라메터를 전달 받을수 있음.
	//http://localhost:8000/blog/dummy/user/3
	@GetMapping("dummy/user/{id}")
	public User detail(@PathVariable int id) {
		
		//user/4를 찾으면 db에서 못찾아서 user가 null이되고 return null이 되니까
		//프로그램에 지장이생김, 그래서 Optional로 User객체를 감싸서 가져와서
		//null인지 아닌지 판단해서 return을해
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				return new IllegalArgumentException("해당 유저는 없습니다. <br>id : "+id);
			}
		});
		//요청:웹브라우저
		//user 객체 = 자바 오브젝트
		//변환(웹부러우저가 이해할수있는 데이터) json
		//만약에 자바 객체를 리턴하게되면 MessageConveter가 Jackson 라이브러리를 통해서
		//user 오브젝트를 json으로 변환해서 브라우저에게 던짐
		return user;
	}
	@PostMapping("dummy/join")
	public String join(User user) {
		System.out.println("id : " + user.getId());
		System.out.println("username : " + user.getUsername() );
		System.out.println("password : " + user.getPassword() );
		System.out.println("email : " + user.getEmail() );
		
		user.setRole(RoleType.USER);
		
		System.out.println("RoleType" + user.getRole());
		userRepository.save(user); //insert
		return "회원가입이 완료되었습니다.";
	}
}
