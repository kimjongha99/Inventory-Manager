// 요청
const onSubmit = (event) => {
    event.preventDefault();

    const data = {
        type : $("#request_type").val(),
        supply : $("#supply").val(),
        content : $("#content").val()
    }

    $.ajax({
        type: "POST",
        url: "request-api/user-request",
        data: JSON.stringify(data),
        dataType: 'text',
        contentType: 'application/json',
    }).done((res, status, xhr) => {
        alert(res);
        window.location.replace("/user-request");
    }).fail((res, status, xhr) => {
        alert("요청 실패");
    })
}