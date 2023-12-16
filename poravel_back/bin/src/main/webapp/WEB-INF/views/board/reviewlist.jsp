<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
  <head>
	<%@ include file="/include/head.jsp" %>	
  </head>
  <body>
	<%@ include file="/include/nav.jsp" %>  
    <div class="container">
      <div class="row justify-content-center">
        <div class="col-lg-8 col-md-10 col-sm-12">
          <h2 class="my-3 py-3 text-center">
            	여행정보공유목록
          </h2>
        </div>
        <div class="col-lg-8 col-md-10 col-sm-12">
          <div class="row align-self-center mb-2">
          	<c:if test="${not empty sessionScope.memberDto}">
            <div class="col-md-2 text-start">
              <button type="button" id="btn-mv-register" class="btn btn-outline-primary btn-sm">
                글쓰기
              </button>
            </div>
            </c:if>
            <div class="col-md-7 offset-3">
              <form class="d-flex" method="GET" id="form-search" action="${root}/board">
              	<input type="hidden" name="action" value="searchList">
                <select
                  id="key"
                  class="form-select form-select-sm ms-5 me-1 w-50"
                  aria-label="검색조건"
                  name="keyfield"
                >
                  <option value="condition" selected>검색조건</option>
                  <option value="articleno">글번호</option>
                  <option value="subject">제목</option>
                  <option value="userid">작성자</option>
                </select>
                <div class="input-group input-group-sm">
                  <input type="text" class="form-control" name="keyword" placeholder="검색어..." />
                  <button class="btn btn-dark" type="button" id="search-btn">검색</button>
                </div>
              </form>
            </div>
          </div>
          <table class="table table-hover">
            <thead>
              <tr class="text-center">
                <th scope="col">번호</th>
                <th scope="col">제목</th>
                <th scope="col">작성자</th>
                <th scope="col">조회수</th>
                <th scope="col">등록일</th>
              </tr>
            </thead>
            <tbody>
	            <c:forEach var="boardDto" items="${requestScope.list}">            	
	              <tr class="text-center">
	                <th scope="row">${boardDto.articleNo}</th>
	                <td class="text-start">
	                  <a
	                    href="${root}/board?action=view&articleNo=${boardDto.articleNo}"
	                    class="article-title link-dark"
	                    data-no="${boardDto.articleNo}"
	                    style="text-decoration: none"
	                  >                
	                  ${boardDto.subject}
	                  </a>
	                </td>
	                <td>${boardDto.userId}</td>
	                <td>${boardDto.hit}</td>
	                <td>${boardDto.registerTime}</td>
	              </tr>
	            </c:forEach>
            </tbody>
          </table>
	        <div class="row">
	          <ul class="pagination justify-content-center">
	            <li class="page-item">
	              <a class="page-link" href="#">이전</a>
	            </li>
	            <li class="page-item"><a class="page-link" href="#">1</a></li>
	            <li class="page-item active">
	              <a class="page-link" href="#">2</a>
	            </li>
	            <li class="page-item"><a class="page-link" href="#">3</a></li>
	            <li class="page-item"><a class="page-link" href="#">4</a></li>
	            <li class="page-item"><a class="page-link" href="#">5</a></li>
	            <li class="page-item"><a class="page-link" href="#">다음</a></li>
	          </ul>
	        </div>
        </div>
      </div>
<!--     </div> -->
	</div>
  <c:if test="${not empty sessionScope.memberDto}">
  <script>
      document.querySelector("#btn-mv-register").addEventListener("click", function () {
        location.href = "${root}/board?action=writeForm";
      });
   </script> 
   </c:if>
   <script>  
      document.querySelector("#search-btn").addEventListener("click", function () {
		  let form = document.querySelector("#form-search");
		  console.log(form);
/*     	  form.setAttribute("action", "${root}/board?action=searchList"); */
    	  form.submit();
      });      
    </script>
<%@ include file="/include/footer.jsp" %>