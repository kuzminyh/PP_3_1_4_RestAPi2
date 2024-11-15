function getEditModal(id) {
    console.log(id);
    let form = document.forms.namedItem("editUserForm");
    console.log(form);
    fillModal(id, form)
}

function getDeleteModal(id) {
    let form = document.forms.namedItem("deleteUserForm");
    fillModal(id, form)
}

function fillModal(id, form) {
    fetch('/admin/' + id)
        .then(response => response.json())
        .then(user => {
            form.elements.namedItem("id").value = user.id;
            form.elements.namedItem("name").value = user.name;
            form.elements.namedItem("surname").value = user.surname;
            form.elements.namedItem("age").value = user.age;
            form.elements.namedItem("username").value = user.username;
            form.elements.namedItem("password").value = user.password;
        })
        .catch(error => console.error('Ошибка:', error));
}