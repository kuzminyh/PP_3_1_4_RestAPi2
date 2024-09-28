document.addEventListener('DOMContentLoaded', function () {
    fetch('/api/admin/getRoles')
        .then(response => response.json())
        .then(roles => {
            const userLink = document.getElementById('userLink');
            const adminLink = document.getElementById('adminLink');

            // Проверяем, существуют ли элементы перед добавлением обработчиков событий
            if (userLink) {
                userLink.addEventListener('click', function () {
                    if (roles.includes('ROLE_USER') || roles.includes('ROLE_ADMIN')) {
                        window.location.href = '/home';
                    } else {
                        showErrorModal();
                    }
                });
            }

            if (adminLink) {
                adminLink.addEventListener('click', function () {
                    if (roles.includes('ROLE_ADMIN')) {
                        window.location.href = '/admin/users';
                    } else {
                        showErrorModal();
                    }
                });
            }
        })
        .catch(error => {
            console.error('Ошибка при получении ролей:', error);
        });
});

function showErrorModal() {
    var modal = new bootstrap.Modal(document.getElementById('errorModal'));
    modal.show();
}
