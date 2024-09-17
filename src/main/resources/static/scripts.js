// Добавляем обработчик для всех кнопок с id "editUserButton"
document.addEventListener('DOMContentLoaded', function () {
    const editButtons = document.querySelectorAll('#editUserButton');

    editButtons.forEach(button => {
        button.addEventListener('click', function () {
            const userId = this.getAttribute('data-user-id');
            openEditUserModal(userId);
        });
    });
});
document.getElementById('editUserForm').addEventListener('submit', function(event) {
    const passwordField = document.getElementById('editPassword');
    if (passwordField.value === '') {
        passwordField.remove();
    }
});
function openEditUserModal(userId) {
    fetch(`/getUser?userId=${userId}`)
        .then(response => response.json())
        .then(user => {
            console.log("Данные пользователя:", user);

            const editUserIdField = document.getElementById('editUserId');
            const editUserNameField = document.getElementById('editUserName');
            const editEmailField = document.getElementById('editEmail');
            const editPasswordField = document.getElementById('editPassword');

            editUserIdField.value = user.id;
            editUserNameField.value = user.username;
            editEmailField.value = user.email;
            editPasswordField.value = ''; // Пароль не отображаем

            editUserNameField.classList.add('bg-warning');
            editEmailField.classList.add('bg-warning');
            editUserNameField.addEventListener('focus', function() {
                editUserNameField.classList.remove('bg-warning');
            });

            editEmailField.addEventListener('focus', function() {
                editEmailField.classList.remove('bg-warning');
            });

            editPasswordField.addEventListener('focus', function() {
                editPasswordField.classList.remove('bg-warning');
            });

            document.getElementById('role_admin_edit').checked = user.roles.some(role => role.id === 1);
            document.getElementById('role_user_edit').checked = user.roles.some(role => role.id === 2);

            const modal = new bootstrap.Modal(document.getElementById('editUserModal'));
            modal.show();
        })
        .catch(error => console.error("Ошибка при загрузке данных пользователя:", error));
}

