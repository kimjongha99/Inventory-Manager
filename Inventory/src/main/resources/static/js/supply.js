// 제품 등록
const onSubmitSupply = (event) => {
    event.preventDefault();

    const data = {
        category : $("#category").val(),
        amount : $("#amount").val(),
        modelName : $("#modelName").val()
    }

    $.ajax({
        type: "POST",
        url: "supply-api/register-supply",
        data: JSON.stringify(data),
        dataType: 'text',
        contentType: 'application/json',
    }).done((res, status, xhr) => {
        alert(res);
        window.location.replace("/register-supply");
    }).fail((res, status, xhr) => {
        alert("상품 등록 실패");
    })
}
