document.addEventListener('DOMContentLoaded', function () {
    alert(5);
    getAllUsers();
});

function getAllUsers() {
    const allUsersTBodyJS = document.getElementById("allUsersTBody");

    fetch('/admin')
        .then(response => response.json())
        .then(allUsers => {
            console.log(allUsers);
            allUsersTBodyJS.innerHTML = '';
            for (const user of allUsers) {
                let row = allUsersTBodyJS.insertRow();
                row.insertCell().innerHTML = user.id;
                row.insertCell().innerHTML = user.name;
                row.insertCell().innerHTML = user.surname;
                row.insertCell().innerHTML = user.age;
                row.insertCell().innerHTML = user.username;
                row.insertCell().innerHTML = user.roles.map(role => role.roleName).join(', ');
                row.insertCell().innerHTML =
                    '<button class="btn btn-sm btn-info" onclick="getEditModal(' + user.id + ')" data-bs-toggle="modal" data-bs-target="#editModal">Edit</button>';
                row.insertCell().innerHTML =
                    '<button class="btn btn-sm btn-danger" onclick="getDeleteModal(' + user.id + ')" data-bs-toggle="modal" data-bs-target="#deleteModal">Delete</button>';
            }
        })
        .catch(error => console.error('Ошибка:', error));
}