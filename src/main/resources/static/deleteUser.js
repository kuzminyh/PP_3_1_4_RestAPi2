const deleteForm = document.forms.namedItem("deleteUserForm");

deleteForm.addEventListener("submit", deleteUserJS);

function deleteUserJS(e) {
    e.preventDefault();

    const closeButton = document.getElementById("closeDelete")
    let id = deleteForm.elements.namedItem("id").value

    fetch('/admin/' + id, {
        method: 'DELETE',
        headers: {"Content-type": "application/json; charset=UTF-8"}
    })
        .then(response => {
            getAllUsers();
            closeButton.click();
        })
        .catch(error => console.error('Ошибка:', error));
}