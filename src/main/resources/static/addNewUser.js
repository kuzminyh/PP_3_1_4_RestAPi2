const addNewUserFormJS = document.forms.namedItem("addNewUserForm");

addNewUserFormJS.addEventListener("submit", addNewUser);

function addNewUser(e) {
    e.preventDefault();

    const rolesOption = addNewUserFormJS.elements.namedItem("roles");
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
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            name: addNewUserFormJS.elements.namedItem("name").value,
            surname: addNewUserFormJS.elements.namedItem("surname").value,
            age: addNewUserFormJS.elements.namedItem("age").value,
            username: addNewUserFormJS.elements.namedItem("username").value,
            password: addNewUserFormJS.elements.namedItem("password").value,
            roles: selectedRoles
        }),
    })
        .then(response => {
            getAllUsers();
            document.getElementById("nav-users-tab").click()
            for (const formElem of addNewUserFormJS) {
                formElem.value = "";
            }
            for (const rolesSelect of rolesOption) {
                rolesSelect.selected = false;
            }
        })
        .catch((error) => {
            console.error('Ошибка:', error);
        });
}