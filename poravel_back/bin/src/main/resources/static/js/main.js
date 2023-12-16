const get = (target) => {
    return document.querySelector(target);
};

const defaultMargin = 300;
let triggerMargin = 0;
let triggerHeight = 0;
const cardList = document.querySelectorAll(".card-up");
const cardBtn = document.querySelectorAll(".info-indi-btn");

const saFunc = function () {
    for (const element of cardList) {
        if (!element.classList.contains("show")) {
            if (element.dataset.saMargin) {
                triggerMargin = parseInt(element.dataset.saMargin);
            } else {
                triggerMargin = defaultMargin;
            }

            if (element.dataset.saTrigger) {
                triggerHeight = document.querySelector(element.dataset.saTrigger).getBoundingClientRect().top + triggerMargin;
            } else {
                triggerHeight = element.getBoundingClientRect().top + triggerMargin;
            }

            if (window.innerHeight > triggerHeight) {
                let delay = element.dataset.saDelay ? element.dataset.saDelay : 0;
                setTimeout(function () {
                    element.classList.add("show");
                }, delay);
            }
        }
    }
};

window.addEventListener("load", saFunc);
window.addEventListener("scroll", saFunc);

window.onload = function () {
    const xhr = new XMLHttpRequest();
    const method = "GET";
    const url = "/data/place.json";

    xhr.open(method, url, true);
    xhr.setRequestHeader("Content-Type", "application/text");

    xhr.onreadystatechange = function () {
        if (xhr.readyState === xhr.DONE) {
            if (xhr.status === 200) {
                const resJson = JSON.parse(xhr.responseText);
                const placeData = resJson.item;

                let placeList = document.querySelectorAll(".cardOne");
                for (let i = 0; i < placeData.length; i++) {
                    let now = placeList[i];
                    let placeItem = `
                    <img class="card-img" src="${placeData[i]["image"]}" alt="Card image" />
                        <div class="card-body">
                            <h4 class="card-title">${placeData[i]["addr"]}</h4>
                            <p class="card-text">${placeData[i]["title"]}</p>
                        </div>
                    `;
                    now.innerHTML += placeItem;
                }
            }
        }
    };

    // 요청 보내기
    xhr.send();
};

// 버튼 클릭시 파싱
const $btn2 = get(".btn2");
const $btn3 = get(".btn3");
const $btn4 = get(".btn4");
const $btn5 = get(".btn5");

$btn2.addEventListener("click", () => {
    const xhr = new XMLHttpRequest();
    const method = "GET";
    const url = "assets/data/hotel.json";

    xhr.open(method, url, true);
    xhr.setRequestHeader("Content-Type", "application/text");

    xhr.onreadystatechange = function () {
        if (xhr.readyState === xhr.DONE) {
            if (xhr.status === 200) {
                const resJson = JSON.parse(xhr.responseText);
                const placeData = resJson.hotel;

                let placeList = document.querySelectorAll(".card2");
                for (let i = 0; i < placeData.length; i++) {
                    let now = placeList[i];
                    let placeItem = `
                    <img class="card-img" src="${placeData[i]["image"]}" alt="Card image" />
                        <div class="card-body">
                            <h4 class="card-title">${placeData[i]["addr"]}</h4>
                            <p class="card-text">${placeData[i]["title"]}</p>
                        </div>
                    `;
                    now.innerHTML = placeItem;
                }
            }
        }
    };

    // 요청 보내기
    xhr.send();
});

$btn3.addEventListener("click", () => {
    const xhr = new XMLHttpRequest();
    const method = "GET";
    const url = "assets/data/hotel.json";

    xhr.open(method, url, true);
    xhr.setRequestHeader("Content-Type", "application/text");

    xhr.onreadystatechange = function () {
        if (xhr.readyState === xhr.DONE) {
            if (xhr.status === 200) {
                const resJson = JSON.parse(xhr.responseText);
                const placeData = resJson.hotel;

                let placeList = document.querySelectorAll(".card3");
                for (let i = 0; i < placeData.length; i++) {
                    let now = placeList[i];
                    let placeItem = `
                    <img class="card-img" src="${placeData[i]["image"]}" alt="Card image" />
                        <div class="card-body">
                            <h4 class="card-title">${placeData[i]["addr"]}</h4>
                            <p class="card-text">${placeData[i]["title"]}</p>
                        </div>
                    `;
                    now.innerHTML = placeItem;
                }
            }
        }
    };

    // 요청 보내기
    xhr.send();
});

$btn4.addEventListener("click", () => {
    const xhr = new XMLHttpRequest();
    const method = "GET";
    const url = "assets/data/hotel.json";

    xhr.open(method, url, true);
    xhr.setRequestHeader("Content-Type", "application/text");

    xhr.onreadystatechange = function () {
        if (xhr.readyState === xhr.DONE) {
            if (xhr.status === 200) {
                const resJson = JSON.parse(xhr.responseText);
                const placeData = resJson.hotel;

                let placeList = document.querySelectorAll(".card4");
                for (let i = 0; i < placeData.length; i++) {
                    let now = placeList[i];
                    let placeItem = `
                    <img class="card-img" src="${placeData[i]["image"]}" alt="Card image" />
                        <div class="card-body">
                            <h4 class="card-title">${placeData[i]["addr"]}</h4>
                            <p class="card-text">${placeData[i]["title"]}</p>
                        </div>
                    `;
                    now.innerHTML = placeItem;
                }
            }
        }
    };

    // 요청 보내기
    xhr.send();
});

$btn5.addEventListener("click", () => {
    const xhr = new XMLHttpRequest();
    const method = "GET";
    const url = "assets/data/hotel.json";

    xhr.open(method, url, true);
    xhr.setRequestHeader("Content-Type", "application/text");

    xhr.onreadystatechange = function () {
        if (xhr.readyState === xhr.DONE) {
            if (xhr.status === 200) {
                const resJson = JSON.parse(xhr.responseText);
                const placeData = resJson.hotel;

                let placeList = document.querySelectorAll(".card5");
                for (let i = 0; i < placeData.length; i++) {
                    let now = placeList[i];
                    console.log(now);
                    let placeItem = `
                    <img class="card-img" src="${placeData[i]["image"]}" alt="Card image" />
                        <div class="card-body">
                            <h4 class="card-title">${placeData[i]["addr"]}</h4>
                            <p class="card-text">${placeData[i]["title"]}</p>
                        </div>
                    `;
                    now.innerHTML = placeItem;
                }
            }
        }
    };

    // 요청 보내기
    xhr.send();
});
