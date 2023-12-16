<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
  <head>
    <%@ include file="/include/head.jsp" %>    
     <link rel="stylesheet" href="${root}/assets/css/place.css" />
     <script src="${root}/assets/js/key.js"></script>
     <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=ab09f8bf81eff8e9d37db44d3ea254a0"></script>
  </head>
  <body>
      <%@ include file="/include/nav.jsp" %>

    <main class="container">
        <div class="alert alert-primary mt-3 text-center fw-bold" role="alert">전국 관광지 정보</div>
        <!-- 관광지 검색 start -->
        <form class="my-3" onsubmit="return false;" role="search">
            <select id="search-area" class="form-select me-2">
                <option value="0" selected>검색 할 지역 선택</option>
            </select>

            <div id="search-content-id" class="fordklfj asl;dm-select me-2">
                <input id="radio1" type="radio" name="tcategory" value="0" checked="checked" />
                <label for="radio1">관광지유형</label>
                <input id="radio2" type="radio" name="tcategory" value="12" />
                <label for="radio2">관광지</label>
                <input id="radio3" type="radio" name="tcategory" value="14" />
                <label for="radio3">문화시설</label>
                <input id="radio4" type="radio" name="tcategory" value="15" />
                <label for="radio4">축제공연행사</label>
                <input id="radio5" type="radio" name="tcategory" value="25" />
                <label for="radio5">여행코스</label>
                <input id="radio6" type="radio" name="tcategory" value="28" />
                <label for="radio6">레포츠</label>
                <input id="radio7" type="radio" name="tcategory" value="32" />
                <label for="radio7">숙박</label>
                <input id="radio8" type="radio" name="tcategory" value="38" />
                <label for="radio8">쇼핑</label>
                <input id="radio9" type="radio" name="tcategory" value="39" />
                <label for="radio9">음식점</label>
            </div>
            <input id="search-keyword" class="form-control me-2" type="search" placeholder="검색어" aria-label="검색어" />
        </form>
        <!-- kakao map start -->
        <div id="map" class="mt-3"></div>
        <!-- kakao map end -->
        <div class="d-flex row search-list justify-content-center mx-auto" style="display: none">
            <div id="search-title"></div>
            <div id="trip-list"></div>
        </div>
        <!-- 관광지 검색 end -->
    </main>

    <%-- --%>
<script src="${root}/assets/js/placeInfo.js"></script>
<%@ include file="/include/footer.jsp" %>