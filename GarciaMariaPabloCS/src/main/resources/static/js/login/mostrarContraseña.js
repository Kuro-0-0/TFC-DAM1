document.addEventListener('DOMContentLoaded', function() { // Cuando la pagina este cargada
    document.getElementById('togglePassword').addEventListener('click', function() { // Le metemos un listener de click al elemento de id togglePassword
        const password = document.getElementById('password'); // Pillamos el input contraseña
        const type = password.type === 'password' ? 'text' : 'password'; // Con un operador ternario cambiamos entre tipos text y contraseña
        password.type = type; // Metemos el tipo que toca
        this.innerHTML = type === 'password' ? '<i class="bi bi-eye"></i>' : '<i class="bi bi-eye-slash"></i>'; // Cambiamos el emote en funcion del tipo.
    });
});