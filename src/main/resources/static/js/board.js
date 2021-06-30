let index = {
	init : function(){
		$("#btn-save").on("click",()=>{ //첫번째 파라미터는 이벤트(클릭) , 두번째 파라미터는 함수를 넣는다(클릭했을때 동작)
			this.save();
		});

		$("#btn_delete").on("click",()=>{ //첫번째 파라미터는 이벤트(클릭) , 두번째 파라미터는 함수를 넣는다(클릭했을때 동작)
        	this.deleteByid();
        });

        $("#btn_update").on("click",()=>{ //첫번째 파라미터는 이벤트(클릭) , 두번째 파라미터는 함수를 넣는다(클릭했을때 동작)
            this.update();
        });

         $("#btn-reply-save").on("click",()=>{ //첫번째 파라미터는 이벤트(클릭) , 두번째 파라미터는 함수를 넣는다(클릭했을때 동작)
             this.replySave();
         });

	},
	// 글쓰기---------------------
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

    // 삭제---------------------
	deleteByid:function(){

	        let id = $("#id").text();
    		$.ajax({
    			type:"DELETE",
    			url:"/api/board/"+id,
    			dataType:"json"
    		}).done(function(resp){
    			alert("게시글이 삭제 되었습니다");
    			console.log(resp);
    			location.href = "/";
    		}).fail(function(error){ //응답의 결과가 비정상이면 실행
    			alert(JSON.stringify(error));
    		});
    	},


    // 업데이트---------------------
    update:function(){

        	        let id = $("#id").val();

            		let data = {
            			title : $("#title").val(),
            			content : $("#content").val()
            		};
            		console.log(id);
            		console.log(data);

            		$.ajax({
            			type:"PUT",
            			url:"/api/board/"+id,
            			data : JSON.stringify(data),
            			contentType : "application/json; charset = utf-8",
            			dataType:"json"
            		}).done(function(resp){
            			alert("게시글이 수정 되었습니다");
            			console.log(resp);
            			location.href = "/";
            		}).fail(function(error){ //응답의 결과가 비정상이면 실행
            			alert(JSON.stringify(error));
            		});
            	},


        replySave:function(){
        		let data = {
        		    userId: $("#userId").val(),
        		    boardId: $("#boardId").val(),
                	content: $("#reply-content").val()
                };

        		$.ajax({
        			type:"POST",
        			url:`/api/board/${data.boardId}/reply`,
        			data : JSON.stringify(data),
        			contentType : "application/json; charset = utf-8",
        			dataType:"json"
        		}).done(function(resp){
        			alert("댓글 작성 완료");
        			console.log(resp);
        			location.href = `/board/${data.boardId}`;
        		}).fail(function(error){ //응답의 결과가 비정상이면 실행
        			alert(JSON.stringify(error));
        		});
        	}


	
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