const table = document.getElementById('userTable');
const tableBody = document.getElementById('userTableBody');

async function fetchUserData() {
    try {
        const response = await axios.get('/sign-api/allUserListForAdmin');
        const users = response.data;

        users.forEach(user => {
            const row = tableBody.insertRow(-1);

            const cell1 = row.insertCell(0);
            const cell2 = row.insertCell(1);
            const cell3 = row.insertCell(2);
            const cell4 = row.insertCell(3);
            const cell5 = row.insertCell(4);
            const cell6 = row.insertCell(5);
            const cell7 = row.insertCell(6);
            const cell8 = row.insertCell(7);

            cell1.innerHTML = user.username;
            cell2.innerHTML = user.email;
            cell3.innerHTML = user.tel;
            cell4.innerHTML = user.roles;
            cell5.innerHTML = user.team;

            const editButton = document.createElement('button');
            editButton.className = 'btn btn-primary';
            editButton.textContent = '팀 설정';
            editButton.addEventListener('click', () => {
                const teamSelect = document.createElement('select');
                teamSelect.className = 'form-control';
                teamSelect.name = 'teamSelect';
                teamSelect.innerHTML = `
                    <option value="" disabled selected>팀을 선택하세요</option>
                    <option value="1반">1반</option>
                    <option value="2반">2반</option>
                    <option value="3반">3반</option>
                    <option value="4반">4반</option>
                    <option value="5반">5반</option>
                    <option value="6반">6반</option>
                    <option value="7반">7반</option>
                    <option value="8반">8반</option>
                    <option value="9반">9반</option>
                    <option value="10반">10반</option>
                `;

                const updateButton = document.createElement('button');
                updateButton.className = 'btn btn-success';
                updateButton.textContent = '수정';
                updateButton.addEventListener('click', () => {
                    const selectedTeam = teamSelect.value;
                    if (selectedTeam) {
                        sendTeamToServer(user.email, selectedTeam);
                        cell5.innerHTML = selectedTeam;
                    } else {
                        cell5.innerHTML = '팀이 선택되지 않았습니다.';
                    }
                    cell6.innerHTML = '';
                    cell6.appendChild(editButton);
                });

                cell6.innerHTML = '';
                cell6.appendChild(teamSelect);
                cell6.appendChild(updateButton);
            });

            cell6.appendChild(editButton);

            const grantRoleButton = document.createElement('button');
            if (user.roles === 'MANAGER') {
                grantRoleButton.textContent = '권한 뺏기';  // Change the button text
                grantRoleButton.className = 'btn btn-danger';  // Change class for red color
            } else {
                grantRoleButton.textContent = '권한 수정';  // Default text
                grantRoleButton.className = 'btn btn-success';  // Default class for green color
            }

            grantRoleButton.addEventListener('click', () => {
                if (user.roles === 'MANAGER') {
                    revokeRole(user.email);  // Change the function for revoking role
                } else {
                    grantRole(user.email);  // Default function for granting role
                }
            });

            cell8.appendChild(grantRoleButton);

            const infoLink = document.createElement('a');
            infoLink.href = `/sign-api/manager/userinfo?email=${user.email}`;
            infoLink.textContent = '정보 보기';
            cell7.appendChild(infoLink);
        });

        table.style.display = 'table';
    } catch (error) {
        console.error('사용자 데이터를 가져오는 중 오류 발생:', error);
    }
}

async function sendTeamToServer(email, selectedTeam) {
    try {
        const response = await axios.post('/sign-api/updateteam', {
            email: email,
            team: selectedTeam
        });
        console.log(response.data);
    } catch (error) {
        console.error('팀 정보 업데이트 중 오류 발생:', error);
    }
}

function grantRole(email) {
    fetch(`/sign-api/roles/${email}`, { method: 'PUT' })
        .then(function (response) {
            // alert("권한 부여에 성공하였습니다.");
            window.location.href = '/ManagerPage';
        })
        .catch(error => console.error('권한 부여 중 오류 발생:', error));
}

function revokeRole(email) {
    fetch(`/sign-api/roles/${email}`, { method: 'PUT' })
        .then(function (response) {
            // alert("권한 뺏기에 성공하였습니다.");
            window.location.href = '/ManagerPage';
        })
        .catch(error => console.error('권한 뺏기 중 오류 발생:', error));
}

function goBack() {
    window.history.back();
}

fetchUserData();