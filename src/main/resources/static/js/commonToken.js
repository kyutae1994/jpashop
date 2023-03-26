function fer() {
    fetch('http://localhost:8000/login/', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        // body: JSON.stringify({
        //     'id': 'kim',
        //     'password': '1234'
        // })
    })
        .then(response => response.json())
        .then(response => {
            if (response.token) {
                localStorage.setItem('wtw-token', response.token);
            }
        })
}