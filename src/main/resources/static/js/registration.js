document.getElementById('registerButton').addEventListener('click', function (event) {
    event.preventDefault(); // Prevent the default form submission

    const username = document.getElementById('username').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const passwordConfirmation = document.getElementById('passwordConfirmation').value;

    const userData = {
        username: username,
        email: email,
        password: password,
    };

    fetch('/api/registration?passwordConfirmation=' + encodeURIComponent(passwordConfirmation), {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(userData),
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text); });
            }
            return response.text(); // Use response.text() to handle the text response from the server
        })
        .then(() => {
            alert('Registration successful!');
            window.location.href = '/login'; // Redirect to login page
        })
        .catch(error => {
            console.error('Ошибка при регистрации:', error.message);
            alert('Ошибка: ' + error.message);
        });
});
