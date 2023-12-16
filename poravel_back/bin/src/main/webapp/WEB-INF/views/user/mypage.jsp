<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	  <%@ include file="/include/head.jsp" %>
	  <style>
 	  	#pwCheck {
	  		display:none;
	  	}
	  </style>	  
	</head>
	<body>
	<%@ include file="/include/nav.jsp" %>
	<!-- section start -->
    <div class="container">
      <div class="row justify-content-center">
        <div class="col-lg-8 col-md-10 col-sm-12">
          <h2 class="my-3 py-3 text-center">
            	마이페이지
          </h2>
        </div>
        <div class="col-lg-8 col-md-10 col-sm-12">
          <form id="form-join" method="POST" action="">
            <div class="mb-3">
              <label for="username" class="form-label">이름 : </label>
              <input
                type="text"
                class="form-control"
                id="username"
                name="userName"
                placeholder="이름..."
                value="${memberDto.userName}"
              />
            </div>
            <div class="mb-3">
              <label for="userid" class="form-label">아이디 : </label>
              <input
                type="text"
                class="form-control"
                id="userid"
                name="userId"
                placeholder="아이디..."
                value="${sessionScope.memberDto.userId}"
              />
            </div>
            <div id="result-view" class="mb-3"></div>
            <div class="mb-3">
              <label for="userpwd" class="form-label">비밀번호 : </label>
              <input
                type="password"
                class="form-control"
                id="userpwd"
                name="userPassword"
                placeholder="비밀번호..."
                value="${memberDto.userPassword}"
              />
            </div>
            <div class="mb-3" id="pwCheck">
              <label for="pwdcheck" class="form-label">비밀번호확인 : </label>
              <input type="password" class="form-control" id="pwdcheck" placeholder="비밀번호확인..." 
              	value="${memberDto.userPassword}"/>
            </div>
            <div class="mb-3">
              <label for="emailid" class="form-label">이메일 : </label>
              <div class="input-group">
                <input
                  type="text"
                  class="form-control"
                  id="emailid"
                  name="emailId"
                  placeholder="이메일아이디"
                  value="${memberDto.emailId}"
                />
                <span class="input-group-text">@</span>
                <select
                  class="form-select"
                  id="emaildomain"
                  name="emailDomain"
                  aria-label="이메일 도메인 선택"
                >
                  <option>선택</option>
                  <option value="ssafy.com">싸피</option>         
                  <option value="google.com">구글</option>
                  <option value="naver.com">네이버</option>
                  <option value="kakao.com">카카오</option>
                </select>
              </div>
            </div>
            <div class="col-auto text-center">
              <button type="button" id="btn-modify" class="btn btn-outline-primary mb-3" >수정</button>
              <button type="button" id="btn-delete" class="btn btn-outline-danger mb-3">회원탈퇴</button>
            </div>
          </form>
        </div>
      </div>
    </div> 
	<script>
	    // 모든 input 요소를 찾아서 readonly 속성을 설정합니다.
	    var inputElements = document.querySelectorAll('input');
	    for (var i = 0; i < inputElements.length; i++) {
	        inputElements[i].readOnly = true;
/* 	        inputElements[i].disabled = true; */
	    }
	    
	    // emailDomain selected 설정하기
	    window.onload = function() {
	    	var optionElements = document.querySelectorAll('option');
		    for (var i = 0; i < optionElements.length; i++) {
		    	if(optionElements[i].value === "${memberDto.emailDomain}") {
		    		console.log("${memberDto.emailDomain}");
		    		optionElements[i].selected = true;
		    	}
		    }
		    
		    var selectElement = document.querySelectorAll('select');
		    console.log(selectElement);
		    selectElement.disabled=true;
	    }
   
      // 수정 버튼 눌렸을때
	  var modifyBtn = document.querySelector("#btn-modify");
      modifyBtn.addEventListener("click", function () {
	      console.log(modifyBtn);
    	  if(modifyBtn.innerHTML === "수정") {
	  	    // 모든 input 요소를 찾아서 readonly 속성을 해제합니다.
	  	    var inputElements = document.querySelectorAll('input');
	  	    for (var i = 0; i < inputElements.length; i++) {
	  	    	if(i == 1) continue; // id는 항상 readOnly여야
	  	        inputElements[i].readOnly = false;
	  	    }
	  	    document.querySelector("#pwCheck").style.display="block";
	  	  	modifyBtn.innerHTML="수정완료";    		  
    	  }
    	  else if(modifyBtn.innerHTML === "수정완료") {  	  		
    		// 여기서부터 controller 호출
  	        if (!document.querySelector("#username").value) {
  	          alert("이름 입력!!");
  	          return;
  	        } else if (!document.querySelector("#userid").value) {
  	          alert("아이디 입력!!");
  	          return;
  	        } else if (!document.querySelector("#userpwd").value) {
  	          alert("비밀번호 입력!!");
  	          return;
  	        } else if (
  	          document.querySelector("#userpwd").value != document.querySelector("#pwdcheck").value) {
  	          alert("비밀번호 확인 불일치!!");
  	          return;
  	        } else {
  	  	    // 모든 input 요소를 찾아서 readonly 속성을 설정합니다.
  	  	      var inputElements = document.querySelectorAll('input');
  	  	      for (var i = 0; i < inputElements.length; i++) {
  	  	          inputElements[i].readOnly = true;
  	  	      }  
  	  	    
  	  		  modifyBtn.innerHTML="수정";
  	  		  document.querySelector("#pwCheck").style.display="none";
  	          
  	  		  let form = document.querySelector("#form-join");
  	          form.setAttribute("action", "${root}/member?action=modify");
   	          form.submit();
  	        }
    		
    	 }
      }); 
      
      // 삭제버튼 눌렸을때
      document.querySelector("#btn-delete").addEventListener("click", function() {
      	alert("회원탈퇴완료되었습니다.");
    	location.href = "${root}/member?action=delete&userId=${memberDto.userId}";    	  
      });
    </script>	
	
	<!-- section end -->
	<%-- --%>
<%@ include file="/include/footer.jsp" %>