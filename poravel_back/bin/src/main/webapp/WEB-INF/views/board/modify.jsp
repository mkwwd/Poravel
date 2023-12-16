<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ssafy.trip.dto.BoardDto" %>
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
            	글수정
          </h2>
        </div>
        <div class="col-lg-8 col-md-10 col-sm-12">
          <form id="form-modify" method="POST" action="">
            <div class="mb-3">
              <label for="userid" class="form-label">작성자 ID : </label>
              <input type="text" class="form-control" name="userid" id="userid" value="${boardDto.userId}" readonly />
            </div>
            <div class="mb-3">
              <label for="subject" class="form-label">제목 : </label>
              <input type="text" class="form-control" name="subject" id="subject" value="${boardDto.subject}" />
            </div>
            <div class="mb-3">
              <label for="content" class="form-label">내용 : </label>
              <textarea class="form-control" name="content" id="content" rows="7">${boardDto.content}
              </textarea>
            </div>
            <div class="col-auto text-center">
              <button type="button" id="btn-modify" class="btn btn-outline-primary mb-3">
                글수정
              </button>
              <button type="button" id="btn-list" class="btn btn-outline-danger mb-3">
                목록으로이동...
              </button>
            </div>
          </form>
        </div>
      </div>
      
    </div>
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
      crossorigin="anonymous"
    ></script>
    <script>
      document.querySelector("#btn-modify").addEventListener("click", function () {
        if (!document.querySelector("#subject").value) {
          alert("제목 입력!!");
          return;
        } else if (!document.querySelector("#content").value) {
          alert("내용 입력!!");
          return;
        } else {
          let form = document.querySelector("#form-modify");
          form.setAttribute("action", "${root}/board?action=modify&articleNo=${boardDto.articleNo}");
          form.submit();
        }
      });
      document.querySelector("#btn-list").addEventListener("click", function () {
          location.href = "${root}/board?action=list";
       });      
    </script>
  </body>
</html>
