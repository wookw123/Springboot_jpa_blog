package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
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



//html파일이 아니라 data를 리턴해주는 controller =  RestController
@RestController 
public class DummyControllerTest { 
	
	@Autowired //의존성 주입 DI(Dependency Injection)
	private UserRepository userRepository;
	
	//save함수는 id를 전달하지 않으면 insert를 해주고
	//save함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
	//save함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 해준다.
	//email , password 수정
	
	@DeleteMapping("/dummy/user/{id}")
	public String deleteUser(@PathVariable int id) {
		
		
		try {
			userRepository.deleteById(id);			
		} catch (IllegalArgumentException e) {
			return "삭제에 실패했습니다(해당 ID가 없습니다)";
		}
		return "user : "+ id  + " 삭제되었습니다";
	}
	
	@Transactional //함수종료시 자동 commit됨
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id,@RequestBody User requestUser) {//json데이터를 요청하면  MessageConverter의 
		System.out.println("id : " + id);										//Jackson 이라는 라이브러리가 변환해서 받아준다
		System.out.println("pw : " + requestUser.getPassword());
		System.out.println("email : " + requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에실패했습니다");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		//userRepository.save(user); //update를 할때는 save()를 잘 안쓴다 대신 메소드에 @Transactional 어노테이션을 넣는다
		
		//더티체킹
		return null;
	}
	
	//http://localhost:8000/blog/dummy/user
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}
	
	//한 페이지당 2건의 데이터를 리턴받기
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size =2 , sort = "id" , direction = Sort.Direction.DESC)Pageable pageable){
		Page<User> pagingusers =  userRepository.findAll(pageable);
		List<User> users = pagingusers.getContent();
		return users;
	}
	
	//{id}주소로 파라미터를 전달받을 수 있음.
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		
		//만약에 존재하지 않는 id가 있을경우 null이 리턴되는데
		//이걸 방지하기위해 optional로 null인지 여부를 판단후 return을 한다
		User user =  userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			
			@Override
			public IllegalArgumentException get() {
				// TODO Auto-generated method stub
				return new IllegalArgumentException("존재하지 않는 유저입니다. id : " + id);
			}
		});
		//요청 = 웹 브라우저에서 했다.
		//user 객체 = 자바 오브젝트
		//변환을 해야한다 (웹 브라우저가 이해할 수 있는 데이터 인 json)
		//스프링 부트 = MessageConverter라는 애가 응답시에 자동으로 동작
		//만약에 자바 오브젝트를 이턴하게되면 MessageConverter가 Jackson 이라는 라이브러리를 호출해서
		//user오브젝트를 json으로 변환해서 브라우저로 보낸다.
		return user;
	}
	
	@PostMapping("/dummy/join")
	public String join(User user) {
		System.out.println("username = " + user.getUsername());
		System.out.println("userpw = " + user.getPassword());
		System.out.println("email = " + user.getEmail());
		System.out.println("role = " + user.getRole());
		System.out.println("createdate = " + user.getCreateDate());
		
		user.setRole(RoleType.USER);
		
		userRepository.save(user);
		return "회원가입완료";		
		
	}
}
