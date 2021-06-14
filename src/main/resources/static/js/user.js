let index = {
	init: function() {
		$("#btn-save").on("click", () => {	// function() {}, ()=>{} this를 바인딩하기 위해서!!
			this.save();
		});
		$("#btn-update").on("click", () => {	// function() {}, ()=>{} this를 바인딩하기 위해서!!
			this.update();
		});
	},
	
	save: function() {
		//alert('user의 save함수 호출됨');
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		
		//console.log(data);
		
		// ajax호출시 기본 default가 비동기 호출
		// ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청!!
		// ajax가 통신을 성고하고 나서 json을 리턴해주면 서버가 자동으로 자바 오브젝트로 변환해준다!
		$.ajax({
			// 회원가입 수행 요청
			type: "POST",	// 요청의 메소드 방식
			url: "/auth/joinProc",
			data: JSON.stringify(data),		//http body 데이터
			contentType: "application/json; carset=utf-8",		// body 데이터가 어떤 타입인지(MIME)
			dataType: "json"		// 요청을 서버로해서 응답이 왔을 때 기본적으로 모든것이 String(문자열) But ![생긴게 json이라면 javascript 오브젝트로 변경]
		}).done(function(resp){
			// 성공시 done으로 들어옴
			if(resp.status == 500) {
				alert("회원가입에 실패하였습니다.");
			} else {
				alert("회원가입이 완료되었습니다.");
				location.href = "/";
			}
		}).fail(function(error){
			// 실패시 fail로 들어옴
			alert(JSON.stringify(error));
		});	
	},
	
	update: function() {
		//alert('user의 save함수 호출됨');
		let data = {
			id: $("#id").val(),
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		
		$.ajax({
			type: "PUT",	// 요청의 메소드 방식
			url: "/user",
			data: JSON.stringify(data),		//http body 데이터
			contentType: "application/json; carset=utf-8",		// body 데이터가 어떤 타입인지(MIME)
			dataType: "json"		// 요청을 서버로해서 응답이 왔을 때 기본적으로 모든것이 String(문자열) But ![생긴게 json이라면 javascript 오브젝트로 변경]
		}).done(function(resp){
			// 성공시 done으로 들어옴
			alert("회원수정이 완료되었습니다.");
			location.href = "/";
		}).fail(function(error){
			// 실패시 fail로 들어옴
			alert(JSON.stringify(error));
		});	
	}
}

index.init();



	/*login: function() {
		//alert('user의 save함수 호출됨');
		let data = {
			username: $("#username").val(),
			password: $("#password").val()
		};
		
		//console.log(data);
		
		// ajax호출시 기본 default가 비동기 호출
		// ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청!!
		// ajax가 통신을 성고하고 나서 json을 리턴해주면 서버가 자동으로 자바 오브젝트로 변환해준다!
		$.ajax({
			// 회원가입 수행 요청
			type: "POST",	// 요청의 메소드 방식
			url: "/api/user/login",
			data: JSON.stringify(data),		//http body 데이터
			contentType: "application/json; carset=utf-8",		// body 데이터가 어떤 타입인지(MIME)
			dataType: "json"		// 요청을 서버로해서 응답이 왔을 때 기본적으로 모든것이 String(문자열) But ![생긴게 json이라면 javascript 오브젝트로 변경]
		}).done(function(resp){
			// 성공시 done으로 들어옴
			alert("로그인이 완료되었습니다.");
			location.href = "/";
		}).fail(function(error){
			// 실패시 fail로 들어옴
			alert(JSON.stringify(error));
		});	*/