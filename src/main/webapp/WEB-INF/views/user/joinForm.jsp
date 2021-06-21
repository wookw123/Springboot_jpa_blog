<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">

	<form>
		<div class="form-group">
			<label for="username">Username:</label> <input type="text"	class="form-control" placeholder="Enter username" id="username">
		</div>
		<div class="form-group">
			<label for="password">password:</label> <input type="password"	class="form-control" placeholder="Enter password" id="password">
		</div>
		<div class="form-group">
			<label for="email">Email address:</label> <input type="email" class="form-control" placeholder=	"Enter email" id="email">
		</div>

		<div class="form-group form-check">
			<label class="form-check-label"> <input
				class="form-check-input" type="checkbox"> Remember me
			</label>
		</div>		
	</form>
	
	<button id = "btn-save" class="btn btn-primary">회원가입하기</button>

</div>

<script src = "/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>




