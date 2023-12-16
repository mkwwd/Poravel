<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- header start -->
<header>
    <nav class="navbar navbar-expand-lg bg-black navbar-dark shadow">
        <div class="container">
            <a class="navbar-brand" aria-current="page" href="${root}/index.jsp">Enjoy Trip</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#collapsibleNavbar">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="collapsibleNavbar">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link" aria-current="page" href="${root}/place/place.jsp">지역별여행지</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">나의여행계획</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${root}/board/memory.jsp">Memory</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${root}/board?action=list">여행정보공유</a>
                    </li>
                </ul>
                <c:if test="${empty sessionScope.memberDto}">
	                <ul class="navbar-nav" id="logoutInfo">
	                    <li class="nav-item">
	                        <a class="nav-link" aria-current="page" href="${root}/member?action=joinForm">회원가입</a>
	                    </li>
	                    <li class="nav-item">
	                        <a class="nav-link" aria-current="page" href="${root}/member?action=loginForm">로그인</a>
	                    </li>
	                </ul>
                </c:if>
                <c:if test="${not empty sessionScope.memberDto}">
	                <ul class="navbar-nav" id="loginInfo">
	                    <li class="nav-item">
	                    	<span class="nav-link" id="welcomeName">[${sessionScope.memberDto.userName}]님 안녕하세요</span>
	                    </li>	                
	                    <li class="nav-item">
	                        <a class="nav-link" aria-current="page" href="${root}/member?action=mypageView">마이페이지</a>
	                    </li>
	                    <li class="nav-item">
	                        <a class="nav-link" aria-current="page" href="${root}/member?action=logout">로그아웃</a>
	                    </li>
	                </ul>
                </c:if>
            </div>
        </div>
    </nav>
</header>
<!-- header end -->
