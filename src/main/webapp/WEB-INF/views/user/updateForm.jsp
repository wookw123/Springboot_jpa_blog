<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">

	<form>
	    <input type = "hidden" id = "id" value = "${principal.user.id}"/>
		<div class="form-group">
			<label for="username">Username:</label>
			<input type="text" value = "${principal.user.username}" class="form-control" placeholder="Enter username" id="username" readonly>
		</div>

        <%-- 유저 정보에 oauth가 비어있을 경우에만 비밀번호 수정이 가능하게 한다 --%>

        <c:choose>
            <c:when test= "${empty principal.user.oauth}">
                <div class="form-group">
                    <label for="password">password:</label>
                    <input type="password"	class="form-control" placeholder="Enter password" id="password">
                </div>

                <div class="form-group">
                    <label for="email">Email address:</label>
                    <input type="email" value = "${principal.user.email}" class="form-control" placeholder=	"Enter email" id="email">
                </div>
            </c:when>

            <c:otherwise>
                <div class="form-group">
                    <label for="email">Email address:</label>
                    <input type="email" value = "${principal.user.email}" class="form-control" placeholder=	"Enter email" id="email" readonly>
                </div>
            </c:otherwise>
        </c:choose>

		<div class="form-group form-check">
			<label class="form-check-label"> <input
				class="form-check-input" type="checkbox"> Remember me
			</label>
		</div>		
	</form>
	
	<button id = "btn_update" class="btn btn-primary">회원수정완료</button>

</div>

<script src = "/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>




