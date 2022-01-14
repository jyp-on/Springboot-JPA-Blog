<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<div class="container">
		<a class="btn btn-secondary" href="/">돌아가기</a>
		
		<c:if test="${board.user.id == principal.user.id }">
			<a href="/board/${board.id}/updateForm" class="btn btn-warning">수정</a>
			<button id="btn-delete" class="btn btn-danger">삭제</button>
		</c:if>
		<br/><br/> 
		<div>
			글 번호 : <span id="id"><i>${board.id} </i></span>
			작성자 : <span><i>${board.user.username} </i></span>
		</div>
		
		<br />
			<h3>${board.title }</h3>
		<hr/>
			<div>
			${board.content}
			</div>
		<hr/>
</div>
<script>
      
</script>
<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp"%>



