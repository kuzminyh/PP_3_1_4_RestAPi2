fetch('/userInfo')
    .then(response => response.json())
    .then(user => {
        const usernameHeader = document.getElementById("usernameHeader");
        const rolesHeader = document.getElementById("rolesHeader");

        usernameHeader.innerText = user.username;
        rolesHeader.innerText = user.roles.map(role => role.roleName).join(', ');

        const tableBody = document.getElementById("userData");
        let row = tableBody.insertRow();
        row.insertCell().innerText = user.name;
        row.insertCell().innerText = user.surname;
        row.insertCell().innerText = user.age;
        row.insertCell().innerText = user.username;
        row.insertCell().innerText = user.roles.map(role => role.roleName).join(', ');
    })
    .catch(error => console.error('Error fetching user data:', error));