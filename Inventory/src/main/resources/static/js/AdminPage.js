const table = document.getElementById('userTable');
const tableBody = document.getElementById('userTableBody');

async function fetchUserData() {
    try {
        const response = await axios.get('/user/allUserListForAdmin');
        const users = response.data;

        users.forEach(user => {
            const row = tableBody.insertRow(-1);

            const cell1 = row.insertCell(0);
            const cell2 = row.insertCell(1);
            const cell3 = row.insertCell(2);
            const cell4 = row.insertCell(3);
            const cell5 = row.insertCell(4);

            cell1.innerHTML = user.username;
            cell2.innerHTML = user.email;
            cell3.innerHTML = user.tel;
            cell4.innerHTML = user.roles;

            const grantRoleButton = document.createElement('button');
            if (user.roles === 'MANAGER') {
                grantRoleButton.textContent = '권한 수정';
                grantRoleButton.className = 'btn btn-danger';
            } else {
                grantRoleButton.textContent = '권한 수정';
                grantRoleButton.className = 'btn btn-success';
            }

            grantRoleButton.addEventListener('click', () => {
                if (user.roles === 'MANAGER') {
                    revokeRole(user.email);
                } else {
                    grantRole(user.email);
                }
            });

            cell5.appendChild(grantRoleButton);
        });

        table.style.display = 'table';
    } catch (error) {
        console.error('사용자 데이터를 가져오는 중 오류 발생:', error);
    }
}

function grantRole(email) {
    fetch(`/user/roles/${email}`, { method: 'PUT' })
        .then(function (response) {
            // alert("권한 수정에 성공하였습니다.");
            window.location.href = '/ManagerPage';
        })
        .catch(error => console.error('권한 수정 중 오류 발생:', error));
}

function revokeRole(email) {
    fetch(`/user/roles/${email}`, { method: 'DELETE' })
        .then(function (response) {
            // alert("권한 뺏기에 성공하였습니다.");
            window.location.href = '/ManagerPage';
        })
        .catch(error => console.error('권한 뺏기 중 오류 발생:', error));
}

fetchUserData();