<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
  <head>
  <%@ include file="/include/head.jsp" %>	
  </head>
  <body>
  <%@ include file="/include/nav.jsp" %>
  	<!-- section start -->
    <div class="container">
      <div class="row justify-content-center">
        <div class="col-lg-8 col-md-10 col-sm-12">
          <h2 class="my-3 py-3 text-center">
            	로그인
          </h2>
        </div>
        <div class="col-lg-8 col-md-10 col-sm-12">
	        <c:if test="${not empty requestScope.message}">
		          <div>
		          	${requestScope.message}
		          </div>        
	        </c:if>
        </div>
        <div class="col-lg-8 col-md-10 col-sm-12">
          <form id="form-login" method="POST" action="">
            <div class="form-check mb-3 float-end">
		        <c:if test="${not empty requestScope.userId}">
	              <input
	                class="form-check-input"
	                type="checkbox"
	                value="ok"
	                id="saveid"
	                name="saveId"
	                checked="checked"
	              />     
		        </c:if>
		        <c:if test="${empty requestScope.userId}">
	              <input
	                class="form-check-input"
	                type="checkbox"
	                value="ok"
	                id="saveid"
	                name="saveId"
	              />      
		        </c:if>  		                                             
              <label class="form-check-label" for="saveid"> 아이디저장 </label>
            </div>
            <div class="mb-3">
              <label for="userid" class="form-label">아이디 : </label>
		        <c:if test="${not empty requestScope.userId}">
	              <input
	                type="text"
	                class="form-control"
	                id="userid"
	                name="userId"
	                value="${requestScope.userId}"
	              /> 
		        </c:if>    
		        <c:if test="${empty requestScope.userId}">
	              <input
	                type="text"
	                class="form-control"
	                id="userid"
	                name="userId"
	                placeholder="아이디..."
	              />  
		        </c:if>       		                                              
            </div>
            <div class="mb-3">
              <label for="userpwd" class="form-label">비밀번호 : </label>
              <input
                type="password"
                class="form-control"
                id="userpwd"
                name="userPassword"
                placeholder="비밀번호..."
              />
            </div>
            <div class="col-auto text-center">
              <button type="button" id="btn-login" class="btn btn-outline-primary mb-3">
                로그인
              </button>
              <button type="button" id="btn-join" class="btn btn-outline-success mb-3">회원가입</button>
            </div>
          </form>
        </div>
      </div>
    </div>
    <!-- section end -->

    <script>
      // 로그인 버튼 이벤트 처리
      document.querySelector("#btn-login").addEventListener("click", function () {
        if (!document.querySelector("#userid").value) {
          alert("아이디 입력!!");
          return;
        } else if (!document.querySelector("#userpwd").value) {
          alert("비밀번호 입력!!");
          return;
        } else {
          let form = document.querySelector("#form-login");
          // FrontController 반영: 회원의 모든 요청을 하나의 서블릿에서 받아서 처리: MemberController : @WebServlet("/member")
          form.setAttribute("action", "${root}/member?action=login");
          form.submit();
        }
      });
      
      // 회원가입 버튼 이벤트 처리
      document.querySelector("#btn-join").addEventListener("click", function () {
    	  location.href = "${root}/member?action=joinForm";
      });
    </script>
	<%-- --%>
<%@ include file="/include/footer.jsp" %>