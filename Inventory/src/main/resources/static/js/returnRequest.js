// 반납 요청
function onReturnRequestHandler(event) {

    const requestType = document.querySelector('#header').attributes.value.value;

    const rentalRequestId = event.target.closest('tr').attributes.value.value;
    const category = event.target.closest('tr').querySelector('.category').textContent;
    const supplyId = event.target.closest('tr').querySelector('.supplyId').attributes.value.value;


    const data = {
        type : requestType,
        rentalRequestId : rentalRequestId,
        category : category,
        supplyId : supplyId
    }

    $.ajax({
        type: 'POST',
        url: '/request-api/user-return',
        data: JSON.stringify(data),
        dataType: 'text',
        contentType: 'application/json'
    }).done((res) => {
        alert(res);
        window.location.replace("/request-user/return");
    }).fail(() =>{
        alert("요청 실패");
    })
}