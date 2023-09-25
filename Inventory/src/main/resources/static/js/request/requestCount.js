$.ajax({
    type: "GET",
    url: "/request-api/admin-requestinfo",
    dataType: 'json',
    contentType: 'application/json; charset=UTF-8'
}).done((res) => {
    console.log(res);
    renderCountComponent(res.rentalCount, res.returnCount);
}).fail(() => {
    alert("목록을 불러오는데 실패했습니다.")
})

function renderCountComponent(rentalCount, returnCount) {
    const rentalLink = document.querySelector('.rental-link');
    const returnLink = document.querySelector('.return-link');

    let rentalCountSpan = document.createElement('span');
    rentalCountSpan.textContent = rentalCount;

    let returnCountSpan = document.createElement('span');
    returnCountSpan.textContent = returnCount;

    rentalLink.appendChild(rentalCountSpan);
    returnLink.appendChild(returnCountSpan);
}