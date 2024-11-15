const editUserFormJS = document.forms.namedItem("editUserForm");

editUserFormJS.addEventListener("submit", updateUser);

function updateUser(e) {
    e.preventDefault();

    const closeButton = document.getElementById("closeEdit")
    const rolesOption = editUserFormJS.elements.namedItem("roles");
    let selectedRoles = [];

    for (const rolesSelect of rolesOption) {
        if (rolesSelect.selected) {
            selectedRoles.push({
                id: rolesSelect.value,
                role: rolesSelect.text
            });
        }
    }

    fetch('/admin', {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            id: editUserFormJS.elements.namedItem("id").value,
            name: editUserFormJS.elements.namedItem("name").value,
            surname: editUserFormJS.elements.namedItem("surname").value,
            age: editUserFormJS.elements.namedItem("age").value,
            username: editUserFormJS.elements.namedItem("username").value,
            password: editUserFormJS.elements.namedItem("password").value,
            roles: selectedRoles
        }),
    })
        .then(response => {
            getAllUsers();
            closeButton.click();
            for (const rolesSelect of rolesOption) {
                rolesSelect.selected = false
            }
        })
        .catch((error) => {
            console.error('Ошибка:', error);
        });
}