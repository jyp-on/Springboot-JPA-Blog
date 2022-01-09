let index={
	init:function(){
		$("#btn-save").on("click", ()=>{ //this를 바인딩하기 위해서
			this.save();
		});
		$("#btn-update").on("click", ()=>{ //this를 바인딩하기 위해서
			this.update();
		});
	},
	
	save: function(){
		
		let data = {
			username: $('#username').val(),
			password: $('#password').val(),
			email: $('#email').val()
		};
		
		
		//ajax 호출시 default가 비동기 호출 
		//ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert요청!
		//ajax가 통신을 성공하고 json을 리턴해주면 자동으로 자바 오브젝트로 변경
		$.ajax({
			type: "POST", 
			url: "/auth/joinProc",//데이터를 받아올 페이지
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8", 
			dataType: "json" //받아올 데이터의 형식
		}).done(function(resp){
			alert("회원가입이 완료되었습니다.");
			location.href = "/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});

	},

	update: function(){
		
		let data = {
			id: $('#id').val(),
			username: $('#username').val(),
			password: $('#password').val(),
			email: $('#email').val()
		};
		
		
		//ajax 호출시 default가 비동기 호출 
		//ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert요청!
		//ajax가 통신을 성공하고 json을 리턴해주면 자동으로 자바 오브젝트로 변경
		$.ajax({
			type: "PUT", 
			url: "/user",//데이터를 받아올 페이지
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8", 
			dataType: "json" //받아올 데이터의 형식
		}).done(function(resp){
			alert("회원정보 수정이 완료되었습니다.");
			location.href = "/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});

	}
}

index.init();
