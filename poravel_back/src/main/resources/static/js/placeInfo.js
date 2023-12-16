let areaUrl = "https://apis.data.go.kr/B551011/KorService1/areaCode1?serviceKey=" + serviceKey + "&numOfRows=20&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json";
// fetch(areaUrl, { method: "GET" }).then(function (response) { return response.json() }).then(function (data) { makeOption(data); });
fetch(areaUrl, { method: "GET" })
    .then((response) => response.json())
    .then((data) => makeOption(data));

function makeOption(data) {
    let areas = data.response.body.items.item;
    // console.log(areas);
    let sel = document.getElementById("search-area");
    areas.forEach((area) => {
        let opt = document.createElement("option");
        opt.setAttribute("value", area.code);
        opt.appendChild(document.createTextNode(area.name));

        sel.appendChild(opt);
    });
}

// HTML5의 geolocation으로 사용할 수 있는지 확인합니다
if (navigator.geolocation) {
    // GeoLocation을 이용해서 접속 위치를 얻어옵니다
    navigator.geolocation.getCurrentPosition(function (position) {
        var lat = position.coords.latitude, // 위도
            lon = position.coords.longitude; // 경도

        var locPosition = new kakao.maps.LatLng(lat, lon), // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
            message = "현위치"; // 인포윈도우에 표시될 내용입니다

        // 마커와 인포윈도우를 표시합니다
        nowMarker(locPosition, message);
    });
} else {
    // HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 인포윈도우 내용을 설정합니다

    var locPosition = new kakao.maps.LatLng(33.450701, 126.570667),
        message = "geolocation을 사용할수 없어요..";

    nowMarker(locPosition, message);
}

// radio 클릭시 위치 변경
const radios = document.querySelectorAll("[name=tcategory]");
radios.forEach((radio) => {
    radio.addEventListener("change", (e) => {
        if (document.getElementById("search-area").value == 0) {
            document.getElementById("radio1").checked = true;
            alert("지역을 선택해주세요!!");
        } else {
            let title = radio.nextElementSibling.innerHTML;
            document.getElementById("search-title").innerText = title;
            searchStart(radio.value);
        }
    });
});

// 검색 기반 마크찍기
function searchStart(value) {
    let baseUrl = `https://apis.data.go.kr/B551011/KorService1/`;
    // let searchUrl = `https://apis.data.go.kr/B551011/KorService1/searchKeyword1?serviceKey=${serviceKey}&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&listYN=Y&arrange=A`;
    // let searchUrl = `https://apis.data.go.kr/B551011/KorService1/areaBasedList1?serviceKey=${serviceKey}&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&listYN=Y&arrange=A`;

    let queryString = `serviceKey=${serviceKey}&numOfRows=40&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&listYN=Y&arrange=A`;
    let areaCode = document.getElementById("search-area").value;
    let contentTypeId = value;
    let keyword = document.getElementById("search-keyword").value;

    if (parseInt(areaCode)) queryString += `&areaCode=${areaCode}`;
    if (parseInt(contentTypeId)) queryString += `&contentTypeId=${contentTypeId}`;
    // if (!keyword) {
    //   alert("검색어 입력 필수!!!");
    //   return;
    // } else searchUrl += `&keyword=${keyword}`;
    let service = ``;
    if (keyword) {
        service = `searchKeyword1`;
        queryString += `&keyword=${keyword}`;
    } else {
        service = `areaBasedList1`;
    }
    let searchUrl = baseUrl + service + "?" + queryString;

    fetch(searchUrl)
        .then((response) => response.json())
        .then((data) => makeList(data, value));
}

var positions; // marker 배열.
function makeList(data, value) {
    console.log(data);
    document.querySelector(".search-list").setAttribute("style", "display: ;");
    let trips = data.response.body.items.item;
    let tripList = ``;
    positions = [];
    trips.forEach((area) => {
        tripList += `
            <div class="detail-container" onclick="moveCenter(${area.mapy}, ${area.mapx});">
              <div class="detail-img">
                <img src="${area.firstimage}" onerror="this.src='../assets/img/panda.jpg'">
              </div>
              <div class="detail-textarea">
                <div class="detail-title">${area.title}</div>
                <div class="detail-pos">${area.addr1} ${area.addr2}</div>
              </div>
            </div>
          `;

        let markerInfo = {
            title: area.title,
            latlng: new kakao.maps.LatLng(area.mapy, area.mapx),
        };

        positions.push(markerInfo);
    });
    document.getElementById("trip-list").innerHTML = tripList;
    displayMarker(value);
}

var container = document.getElementById("map"); //지도를 담을 영역의 DOM 레퍼런스

var options = {
    //지도를 생성할 때 필요한 기본 옵션
    center: new kakao.maps.LatLng(33.450701, 126.570667), //지도의 중심좌표.
    level: 3, //지도의 레벨(확대, 축소 정도)
};

var map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴

// 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
var mapTypeControl = new kakao.maps.MapTypeControl();

// 지도에 컨트롤을 추가해야 지도위에 표시됩니다
// kakao.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

// 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
var zoomControl = new kakao.maps.ZoomControl();
map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
var infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });

function displayMarker(value) {
    // 마커 이미지의 이미지 주소입니다

    var imageSrc;

    if (value == 12) {
        imageSrc = "../assets/img/pins/red.png";
    } else if (value == 14) {
        imageSrc = "../assets/img/pins/yellow.png";
    } else if (value == 15) {
        imageSrc = "../assets/img/pins/green.png";
    } else if (value == 25) {
        imageSrc = "../assets/img/pins/blue.png";
    } else if (value == 28) {
        imageSrc = "../assets/img/pins/house.png";
    } else if (value == 32) {
        imageSrc = "../assets/img/pins/red.png";
    } else if (value == 38) {
        imageSrc = "../assets/img/pins/green.png";
    } else {
        imageSrc = "../assets/img/pins/yellow.png";
    }

    for (var i = 0; i < positions.length; i++) {
        // 마커 이미지의 이미지 크기 입니다
        var imageSize = new kakao.maps.Size(24, 35);

        // 마커 이미지를 생성합니다
        var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

        // 마커를 생성합니다
        var marker = new kakao.maps.Marker({
            map: map, // 마커를 표시할 지도
            position: positions[i].latlng, // 마커를 표시할 위치
            image: markerImage, // 마커 이미지
        });

        // 마커에 클릭이벤트를 등록합니다
        kakao.maps.event.addListener(marker, "click", function () {
            // 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
            infowindow.setContent('<div style="padding:5px;font-size:12px;">' + positions[i].title + "</div>");
            infowindow.open(map, marker);
        });
    }

    // 첫번째 검색 정보를 이용하여 지도 중심을 이동 시킵니다
    map.setCenter(positions[0].latlng);
}

var overlay;

// 현재 위치 표시
function nowMarker(locPosition, message) {
    // 마커를 생성합니다
    var marker = new kakao.maps.Marker({
        map: map,
        position: locPosition,
    });

    var content = '   <div class="cus-info" >' + '       <div class="cus-title">' + `${message}` + "       </div>" + "   </div>";

    // 마커 위에 커스텀오버레이를 표시합니다
    // 마커를 중심으로 커스텀 오버레이를 표시하기위해 CSS를 이용해 위치를 설정했습니다
    overlay = new kakao.maps.CustomOverlay({
        content: content,
        map: map,
        position: marker.getPosition(),
    });

    // 지도 중심좌표를 접속위치로 변경합니다
    map.setCenter(locPosition);

    overlay.setMap(map);
}

function closeOverlay() {
    overlay.setMap(null);
}

function moveCenter(lat, lng) {
    map.setCenter(new kakao.maps.LatLng(lat, lng));
}
