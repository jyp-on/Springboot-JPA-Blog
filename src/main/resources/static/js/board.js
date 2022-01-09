let index={
	init:function(){
		$("#btn-save").on("click", ()=>{ 
			this.save();
		});
		$("#btn-delete").on("click", ()=>{ 
			this.deleteById();
		});
		$("#btn-update").on("click", ()=>{ 
			this.update();
		});
	},
	save: function(){
		
		
		
		let data = {
			title: $('#title').val(),
			content: $('#content').val()
		};
	
	
		$.ajax({
			type: "POST", 
			url: "/api/board",//데이터를 받아올 페이지
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8", 
			dataType: "json" //받아올 데이터의 형식
		}).done(function(resp){
			alert("글쓰기가 완료되었습니다.");
			location.href = "/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	},
	
	deleteById: function(){
		let id= $('#id').text()
	
		$.ajax({
			type: "DELETE", 
			url: "/api/board/"+id,//데이터를 받아올 페이지
			dataType: "json" //받아올 데이터의 형식
		}).done(function(resp){
			alert("글 삭제가 완료되었습니다.");
			location.href = "/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	},
	update: function(){
		let id = $('#id').val();
		
		let data = {
			title: $('#title').val(),
			content: $('#content').val()
		};
	
	
		$.ajax({
			type: "PUT", 
			url: "/api/board/"+id,//데이터를 받아올 페이지
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8", 
			dataType: "json" //받아올 데이터의 형식
		}).done(function(resp){
			alert("글수정이 완료되었습니다.");
			location.href = "/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	}
}
index.init();


$('.summernote').summernote({
        tabsize: 2,
        height: 300
      });