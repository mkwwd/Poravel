<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="./include/head.jsp" %>
	<link rel="stylesheet" href="/css/main.css" />
</head>
<body>
	<%@ include file="./include/nav.jsp" %>

	<%-- 페이지만의 내용 --%>
        <!-- section start -->
        <!-- Carousel -->
        <div id="demo" class="carousel slide w-100" data-bs-ride="carousel">
            <!-- Indicators/dots -->
            <div class="carousel-indicators">
                <button type="button" data-bs-target="#demo" data-bs-slide-to="0" class="active"></button>
                <button type="button" data-bs-target="#demo" data-bs-slide-to="1"></button>
                <button type="button" data-bs-target="#demo" data-bs-slide-to="2"></button>
            </div>

            <!-- The slideshow/carousel -->
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <img src="/img/paris.jpg" alt="Paris" class="d-block" />
                    <div class="carousel-caption">
                        <h3>Paris</h3>
                        <p>We had such a great time in Paris!</p>
                    </div>
                </div>
                <div class="carousel-item">
                    <img src="/img/newyork.jpg" alt="NewYork" class="d-block" />
                    <div class="carousel-caption">
                        <h3>NewYork</h3>
                        <p>Thank you, NewYork!</p>
                    </div>
                </div>
                <div class="carousel-item">
                    <img src="/img/australia.jpg" alt="Australia" class="d-block" />
                    <div class="carousel-caption">
                        <h3>Australia</h3>
                        <p>We love Australia!</p>
                    </div>
                </div>
            </div>

            <!-- Left and right controls/icons -->
            <button class="carousel-control-prev" type="button" data-bs-target="#demo" data-bs-slide="prev">
                <span class="carousel-control-prev-icon"></span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#demo" data-bs-slide="next">
                <span class="carousel-control-next-icon"></span>
            </button>

<!--             <div class="col-lg-3 pop-up">
                <div class="card m-1 px-2">
                    <div class="card-body">
                        <h3 class="card-title">문의하기!</h3>
                        <p class="card-text">이름과 연락처를 남겨주세요.</p>
                        <form action="" method="get">
                            <input type="text" name="name" id="name" class="form-control w-100 mb-2" placeholder="이름" />
                            <input type="number" name="number" id="number" class="form-control w-100 mb-2" placeholder="전화번호" />
                            <div class="form-check mb-2">
                                <input class="form-check-input" type="checkbox" value="" id="flexCheckDefault" />
                                <label class="form-check-label" for="flexCheckDefault"> 정보제공동의함 </label>
                            </div>
                        </form>
                        <a href="#" class="w-100 btn btn-primary text-center">보내기</a>
                    </div>
                </div>
            </div> -->
        </div>

        <section class="p-5 section1 bg-light">
            <div class="container my-5">
                <div class="d-flex row justify-content-center">
                    <h2 class="card-up">지역별여행지</h2>
                    <!-- <h4></h4> -->
                    <div class="col-12 card-up">
                        <div id="place-info" class="carousel slide">
                            <!-- Indicators/dots -->
                            <div class="container mb-5">
                                <button type="button" data-bs-target="#place-info" data-bs-slide-to="0" class="info-indi-btn bg-light">관광명소</button>
                                <button type="button" data-bs-target="#place-info" data-bs-slide-to="1" class="info-indi-btn btn2 bg-light">숙박업소</button>
                                <button type="button" data-bs-target="#place-info" data-bs-slide-to="2" class="info-indi-btn btn3 bg-light">음식점</button>
                                <button type="button" data-bs-target="#place-info" data-bs-slide-to="3" class="info-indi-btn btn4 bg-light">공연</button>
                                <button type="button" data-bs-target="#place-info" data-bs-slide-to="4" class="info-indi-btn btn5 bg-light">쇼핑</button>
                            </div>

                            <!-- The slideshow/carousel -->
                            <div class="carousel-inner mb-5">
                                <div class="carousel-item active">
                                    <div class="container row mx-auto">
                                        <div class="col-lg-3 card-up" data-sa-delay="100">
                                            <div id="card" class="cardOne card m-1"></div>
                                        </div>
                                        <div class="col-lg-3 card-up" data-sa-delay="300">
                                            <div id="card" class="cardOne card m-1"></div>
                                        </div>
                                        <div class="col-lg-3 card-up" data-sa-delay="500">
                                            <div id="card" class="cardOne card m-1"></div>
                                        </div>
                                        <div class="col-lg-3 card-up" data-sa-delay="700">
                                            <div id="card" class="cardOne card m-1"></div>
                                        </div>
                                    </div>
                                </div>
                                <div class="carousel-item">
                                    <div class="container row mx-auto">
                                        <div class="col-lg-3 card-up" data-sa-delay="100">
                                            <div id="card" class="card2 card m-1"></div>
                                        </div>
                                        <div class="col-lg-3 card-up" data-sa-delay="300">
                                            <div id="card" class="card2 card m-1"></div>
                                        </div>
                                        <div class="col-lg-3 card-up" data-sa-delay="500">
                                            <div id="card" class="card2 card m-1"></div>
                                        </div>
                                        <div class="col-lg-3 card-up" data-sa-delay="700">
                                            <div id="card" class="card2 card m-1"></div>
                                        </div>
                                    </div>
                                </div>
                                <div class="carousel-item">
                                    <div class="container row mx-auto">
                                        <div class="col-lg-3 card-up" data-sa-delay="100">
                                            <div id="card" class="card card3 m-1"></div>
                                        </div>
                                        <div class="col-lg-3 card-up" data-sa-delay="300">
                                            <div id="card" class="card card3 m-1"></div>
                                        </div>
                                        <div class="col-lg-3 card-up" data-sa-delay="500">
                                            <div id="card" class="card card3 m-1"></div>
                                        </div>
                                        <div class="col-lg-3 card-up" data-sa-delay="700">
                                            <div id="card" class="card card3 m-1"></div>
                                        </div>
                                    </div>
                                </div>
                                <div class="carousel-item">
                                    <div class="container row mx-auto">
                                        <div class="col-lg-3 card-up" data-sa-delay="100">
                                            <div id="card" class="card card4 m-1"></div>
                                        </div>
                                        <div class="col-lg-3 card-up" data-sa-delay="300">
                                            <div id="card" class="card card4 m-1"></div>
                                        </div>
                                        <div class="col-lg-3 card-up" data-sa-delay="500">
                                            <div id="card" class="card card4 m-1"></div>
                                        </div>
                                        <div class="col-lg-3 card-up" data-sa-delay="700">
                                            <div id="card" class="card card4 m-1"></div>
                                        </div>
                                    </div>
                                </div>
                                <div class="carousel-item">
                                    <div class="container row mx-auto">
                                        <div class="col-lg-3 card-up" data-sa-delay="100">
                                            <div id="card" class="card card5 m-1"></div>
                                        </div>
                                        <div class="col-lg-3 card-up" data-sa-delay="300">
                                            <div id="card" class="card card5 m-1"></div>
                                        </div>
                                        <div class="col-lg-3 card-up" data-sa-delay="500">
                                            <div id="card" class="card card5 m-1"></div>
                                        </div>
                                        <div class="col-lg-3 card-up" data-sa-delay="700">
                                            <div id="card" class="card card5 m-1"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Left and right controls/icons -->
                        </div>
                    </div>
                </div>
            </div>
        </section>

<!--         <section class="section2 bg-white"></section> -->

        <section class="section3 bg-light">
            <div class="container-fluid p-5 text-secondary mt-5">
                <div class="d-flex row mb-5 ms-5 card-up">
                    <div class="memory-title">
                        <a href="./board/memory.html">
                            Memory
                            <img src="/img/camera.png" alt="" />
                        </a>
                    </div>
                    <div class="memory-sub">추억을 공유하세요</div>
                </div>
                <div class="wrapper col-4 flex-wrap mx-auto mb-5">
                    <div class="pol-item card-up" data-sa-delay="100">
                        <div class="polaroid">
                            <img src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/210284/dXAhQuT.jpg" />
                            <div class="caption">By Joshua Sortino</div>
                        </div>
                    </div>
                    <div class="pol-item card-up" data-sa-delay="200">
                        <div class="polaroid">
                            <img src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/210284/LZkivxR.jpg" />
                            <div class="caption">By Cole Patrick</div>
                        </div>
                    </div>
                    <div class="pol-item card-up" data-sa-delay="300">
                        <div class="polaroid">
                            <img src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/210284/hqcMtrF.jpg" />
                            <div class="caption">By Luke Pamer</div>
                        </div>
                    </div>
                    <div class="pol-item card-up" data-sa-delay="400">
                        <div class="polaroid">
                            <img src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/210284/l867sBU.jpg" />
                            <div class="caption">By Alissa Smith</div>
                        </div>
                    </div>
                    <div class="pol-item card-up" data-sa-delay="500">
                        <div class="polaroid">
                            <img src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/210284/7cQCk5I.jpg" />
                            <div class="caption">By Ales Krivec</div>
                        </div>
                    </div>
                    <div class="pol-item card-up" data-sa-delay="600">
                        <div class="polaroid">
                            <img src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/210284/7cQCk5I.jpg" />
                            <div class="caption">By Ales Krivec</div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

<!--         <section class="section4 bg-white"></section> -->
        <!-- section end -->


<script src="/js/main.js"></script>
<%@ include file="./include/footer.jsp" %>