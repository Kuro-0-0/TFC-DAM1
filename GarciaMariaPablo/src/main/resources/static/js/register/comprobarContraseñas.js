        document.addEventListener('DOMContentLoaded', () => {
            const form = document.querySelector('form');
            form.addEventListener('submit', function (e) {
                const password = document.getElementById('password');
                const confirmPassword = document.getElementById('confirmPassword');

                if (password.value !== confirmPassword.value) {
                    e.preventDefault();
                    confirmPassword.classList.add('is-invalid');
                    const error = document.getElementById('confirmPasswordError');
                    error.textContent = "Las contraseñas no coinciden.";
                    error.classList.remove('d-none');
                }
            });
        });