// approve api
function onApproveRentalHandler(event) {

    const requestId = document.querySelector('#requestId').textContent;
    const supplyId = event.target.closest('tr').querySelector('.supplyId').textContent;

    console.log(requestId);
    console.log(supplyId);

    const data = {
        requestId : requestId,
        supplyId: supplyId
    }

    $.ajax({
        type: "POST",
        url: "/request-api/admin-request/rental-request-approve/",
        data: JSON.stringify(data),
        dataType: 'text',
        contentType: 'application/json'
    }).done((res) => {
        alert(res)
        window.location.replace("/admin-main");
    }).fail(() => {
        alert("요청 실패");
    })

}

// 거절 팝업 열기
function onRejectBtnClickHandler() {
    document.querySelector(".modal-overlay").classList.add('show');
}

// 거절 팝업 닫기
function onCloseBtnClickHandler() {
    document.querySelector("#comment").value = "";
    document.querySelector('.modal-overlay').classList.remove('show');
}

// reject api
function onRejectRentalHandler(event) {
    event.preventDefault();

    const requestId = document.querySelector('#requestId').textContent;
    const comment = document.querySelector('#comment').value;

    const data = {
        requestId : requestId,
        comment : comment
    }

    console.log(data);

    $.ajax({
        type: 'POST',
        url: '/request-api/admin-request/rental-request-reject/',
        data: JSON.stringify(data),
        dataType: 'text',
        contentType: 'application/json'
    }).done((res) => {
        alert(res);
        window.location.replace("/admin-main");
    }).fail(() => {
       alert("요청 실패");
    });

}
