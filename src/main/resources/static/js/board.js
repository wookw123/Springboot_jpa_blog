let index = {
	init : function(){
		$("#btn-save").on("click",()=>{ //첫번째 파라미터는 이벤트(클릭) , 두번째 파라미터는 함수를 넣는다(클릭했을때 동작)
			this.save();
		});

	},
	
	save:function(){
		let data = {
			title : $("#title").val(),
			content : $("#content").val()
		};

		$.ajax({
			type:"POST",
			url:"/api/board",
			data : JSON.stringify(data),
			contentType : "application/json; charset = utf-8",
			dataType:"json"
		}).done(function(resp){
			alert("글쓰기 완료");
			console.log(resp);
			location.href = "/";
		}).fail(function(error){ //응답의 결과가 비정상이면 실행
			alert(JSON.stringify(error));
		}); 
	},
	
//	login:function(){
//		//alert("user의 save함수 호출됨");
//		let data = {
//			username : $("#username").val(),
//			password : $("#password").val(),
//
//		};
//
//
//		$.ajax({
//			type:"POST",
//			url:"/api/user/login",
//			data : JSON.stringify(data),
//			contentType : "application/json; charset = utf-8",
//			dataType:"json"
//		}).done(function(resp){ //응답의 결과가 정상이면 실행
//			alert("로그인 완료");
//			location.href = "/";
//		}).fail(function(error){ //응답의 결과가 비정상이면 실행
//			alert(JSON.stringify(error));
//		});
//	}
}

index.init();