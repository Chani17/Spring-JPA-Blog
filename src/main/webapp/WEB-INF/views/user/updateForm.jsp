<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form>
	<input type="hidden" id="id" value="${principal.user.id}" />
		<div class="form-group">
			<label for="username">Username</label> 
			<input type="text" value="${principal.user.username}" class="form-control" placeholder="Enter username" id="username" readonly>
		</div>

		<!-- oauth값이 있으면, 즉 카카오로 연동해서 로그인일 했다면 패스워드 변경 불가 -->
		<c:if test="${empty principal.user.oauth}">
			<div class="form-group">
				<label for="pwd">Password</label> 
				<input type="password" class="form-control" placeholder="Enter password" id="password">
			</div>
		</c:if>
		
			<div class="form-group">
				<label for="email">Email</label> 
				<input type="email" value="${principal.user.email}" class="form-control" placeholder="Enter email" id="email" readonly>
			</div>


	</form>

	<button id="btn-update" class="btn btn-primary">회원수정완료</button>
</div>

<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>