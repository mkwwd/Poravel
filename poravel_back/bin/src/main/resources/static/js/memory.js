const get = (target) => {
    return document.querySelector(target);
};

const modal = get(".modal");
const form = get("#memory-form");
const addPic = get("#add-picture");
const placeTitle = get("#place-title");
const placeDate = get("#place-date");
const placeMessage = get("#place-message");

var placeList = [];

const renderPlaces = () => {};

const getPlaces = () => {
    let places = localStorage.getItem("places");
    places = JSON.parse(places);
    for (i = 0; i < places.length; i++) {
        placeList[i] = new Array(places[i].pic, places[i].title, places[i].date, places[i].message);
        console.log(places[i].title);
    }
};

form.addEventListener("submit", (e) => {
    e.preventDefault();

    if (!addPic.value || !placeTitle.value || !placeDate.value || !placeMessage.value) {
        alert("비어 있는 칸이 있어요ㅠ");
        return;
    }

    const place = {
        pic: addPic.value,
        title: placeTitle.value,
        date: placeDate.value,
        message: placeMessage.value,
    };

    window.localStorage.setItem("places", JSON.stringify(place));

    alert("추가 완료!!!");
});
