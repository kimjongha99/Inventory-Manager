// 카테고리 목록 불러오기
$(document).ready(() => {

    $.ajax({
        type: "GET",
        url: "/category-api/categorylist",
        dataType: "json",
    }).done((res) => {
        renderCategoryList(res);
    }).fail(() => {
        alert("목록을 불러오는데 실패했습니다.");
    });

});

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

// 제품 대여 요청
const onSubmitRentalRequest = (event) => {
    event.preventDefault();

    const type = document.getElementById('header').attributes.value.value;

    const data = {
        type : type,
        category : $('#category').val(),
        content : $('#content').val()
    }

    $.ajax({
        type: "POST",
        url: "/request-api/user-rental",
        data: JSON.stringify(data),
        dataType: 'text',
        contentType: 'application/json; charset=UTF-8',
    }).done((res) => {
        alert(res);
        window.location.replace("/request-user/rental");
    }).fail(() => {
        alert("요청 실패");
    })
}

// 대여 요청 카테고리
function onCategoryChangeHandler(e) {
    let category = e.target.value;

    window.location.replace("/admin-requestlist/rental?category=" + category);
}

