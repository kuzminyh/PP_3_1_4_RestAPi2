document.addEventListener('DOMContentLoaded', function () {
    getRoles();
});


function getRoles() {

    const allRolesSelect = document.getElementsByName("roles")

    fetch('/admin/roles')
        .then(response => response.json())
        .then(roles => {
            console.log("Любой текст: " + roles)
            for (const select of allRolesSelect) {
                for (const role of roles) {
                    select.append(new Option(role.roleName, role.id));
                }
            }
        })
        .catch(error => console.error('Ошибка:', error));
}