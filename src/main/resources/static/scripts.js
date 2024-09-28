document.addEventListener('DOMContentLoaded', function () {
    loadUserTable();
});

// Функция загрузки и отображения списка пользователей
function loadUserTable() {
    fetch('/api/admin/users')
        .then(response => response.json())
        .then(users => {
            const userTableBody = document.getElementById('userTableBody');
            userTableBody.innerHTML = ''; // Очищаем таблицу

            users.forEach(user => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${user.id}</td>
                    <td>${user.username}</td>
                    <td>${user.email}</td>
                    <td>${user.roles.map(role => role.name).join(', ')}</td>
                    <td>
                        <button class="btn btn-warning btn-sm" onclick="editUser(${user.id})">Изменить</button>
                        <button class="btn btn-danger btn-sm" onclick="deleteUser(${user.id})">Удалить</button>
                    </td>
                `;
                userTableBody.appendChild(row);
            });
        })
        .catch(error => console.error('Ошибка при загрузке пользователей:', error));
}

function editUser(userId) {
    fetch(`/api/admin/users/${userId}`)
        .then(response => response.json())
        .then(user => {
            document.getElementById('editUserId').value = user.id;
            document.getElementById('editUserName').value = user.username;
            document.getElementById('editEmail').value = user.email;

            // Заполняем поле пароля зашифрованным значением из базы
            document.getElementById('editPassword').value = user.password;

            document.getElementById('role_admin_edit').checked = user.roles.some(role => role.id === 1);
            document.getElementById('role_user_edit').checked = user.roles.some(role => role.id === 2);

            const modal = new bootstrap.Modal(document.getElementById('editUserModal'));
            modal.show();
        })
        .catch(error => console.error('Ошибка при загрузке данных пользователя:', error));
}

function deleteUser(userId) {
    fetch(`/api/admin/users/${userId}`, {
        method: 'DELETE'
    })
        .then(response => {
            if (response.ok) {
                alert('Пользователь успешно удален');
                // Обновляем только таблицу пользователей
                loadUserTable();
            } else {
                return response.text().then(text => { throw new Error(text); });
            }
        })
        .catch(error => console.error('Ошибка при удалении пользователя:', error));
}

// Обработчик формы для редактирования пользователя
document.getElementById('editUserForm').addEventListener('submit', function (event) {
    event.preventDefault();

    const userId = document.getElementById('editUserId').value;
    const username = document.getElementById('editUserName').value;
    const email = document.getElementById('editEmail').value;
    const password = document.getElementById('editPassword').value; // Получаем значение пароля
    const roles = [];

    if (document.getElementById('role_admin_edit').checked) roles.push({ id: 1, name: 'ROLE_ADMIN' });
    if (document.getElementById('role_user_edit').checked) roles.push({ id: 2, name: 'ROLE_USER' });

    const data = {
        id: userId,
        username: username,
        email: email,
        password: password,
        roles: roles
    };
    console.log("Отправляемые данные:", JSON.stringify(data));

    fetch(`/api/admin/users/${userId}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text); });
            }
            return response.text();
        })
        .then(() => {
            alert('Данные пользователя обновлены');
            // Закрываем модальное окно
            const modal = bootstrap.Modal.getInstance(document.getElementById('editUserModal'));
            modal.hide();
            loadUserTable();
        })
        .catch(error => {
            console.error('Ошибка при обновлении данных пользователя:', error.message);
            alert(`Ошибка: ${error.message}`);
        });
});

document.getElementById('registerUserForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const username = document.getElementById('registerUserName').value;
    const email = document.getElementById('registerEmail').value;
    const password = document.getElementById('registerPassword').value;
    const passwordConfirmation = document.getElementById('registerPasswordConfirmation').value;
    const roles = [];

    // Получаем выбранные роли
    if (document.getElementById('role_admin_register').checked) roles.push({ id: 1, name: 'ROLE_ADMIN' });
    if (document.getElementById('role_user_register').checked) roles.push({ id: 2, name: 'ROLE_USER' });

    const data = {
        username: username,
        email: email,
        password: password,
        roles: roles
    };

    fetch(`/api/admin/users?passwordConfirmation=${encodeURIComponent(passwordConfirmation)}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text); });
            }
            return response.text();
        })
        .then(() => {
            alert('Пользователь успешно создан');
            location.reload();
        })
        .catch(error => {
            console.error('Ошибка при создании пользователя:', error);
            alert(`Ошибка: ${error.message}`);
        });
});

