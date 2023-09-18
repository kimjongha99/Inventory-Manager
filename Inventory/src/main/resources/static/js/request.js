// 목록 불러오기
$(document).ready(() => {

    $.ajax({
        type: "GET",
        url: "/category-api/categorylist",
        dataType: "json",
    }).done((res, status, xhr) => {
        renderCategoryList(res);
    }).fail((res, status, xhr) => {
        alert("목록을 불러오는데 실패했습니다.");
    });

});

// 제품 요청
const onSubmitRequest = (event) => {
    event.preventDefault();

    const data = {
        type : $("#request_type").val(),
        category : $("#category").val(),
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

// 카테고리 옵션 렌더링
function renderCategoryList(categoryList) {
    const categorySelect = document.getElementById("category");

    for(let i of categoryList) {
        let categoryOption = document.createElement("option");

        categoryOption.value = i.categoryName;
        categoryOption.textContent = i.categoryName;

        categorySelect.appendChild(categoryOption);
    }
}