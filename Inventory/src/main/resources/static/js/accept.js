function acceptRentalHandler(event) {

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
        url: "/request-api/admin-request/rental-request-accept",
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
