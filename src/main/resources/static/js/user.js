let index = {
	init : function(){
		$("#btn-save").on("click",()=>{ //첫번째 파라미터는 이벤트(클릭) , 두번째 파라미터는 함수를 넣는다(클릭했을때 동작)
			this.save();
		}); 
	},
	
	save:function(){
		//alert("user의 save함수 호출됨");
		let data = {
			username : $("#username").val(),
			password : $("#password").val(),			
		};
		
		//console.log(data);
		
		//ajax 호출시 default가 비동기호출이다.
	    //ajax통신을 이용해서 3개의 데이터를 json으로 변경하여 insert요청(위에 3개 username,password,email)
	    //ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바 오브젝트로 변환해준다.
		$.ajax({
			type:"POST",
			url:"/blog/api/user",
			data : JSON.stringify(data), //json으로 변환해서 데이터를 전송 http body데이터
			contentType : "application/json; charset = utf-8", //body데이터가 어떤타입인지 (MIME)
			dataType:"json" //요청을 서버로해서 응답이 왔을때 기본적으로 모든것이 문자열이다. (만약 형식이 json이라면) => javascript오브젝트로 변경 			
		}).done(function(resp){ //응답의 결과가 정상이면 실행
			alert("회원가입 완료");
			console.log(resp);
			location.href = "/blog";
		}).fail(function(error){ //응답의 결과가 비정상이면 실행
			alert(JSON.stringify(error));
		}); 
	},
	
	login:function(){
		//alert("user의 save함수 호출됨");
		let data = {
			username : $("#username").val(),
			password : $("#password").val(),
		
		};
		
		
		$.ajax({
			type:"POST",
			url:"/blog/api/user/login",
			data : JSON.stringify(data), 
			contentType : "application/json; charset = utf-8", 
			dataType:"json"  			
		}).done(function(resp){ //응답의 결과가 정상이면 실행
			alert("로그인 완료");			
			location.href = "/blog";
		}).fail(function(error){ //응답의 결과가 비정상이면 실행
			alert(JSON.stringify(error));
		}); 
	}
}

index.init();