document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("formContacto").addEventListener("submit", function (event) {
        document.getElementById("infoMessage").classList.remove("d-none");
        document.getElementById("infoMessage").scrollIntoView({ behavior: "smooth" });
        const infoText = document.getElementById("infoTextMessage");
        infoText.textContent = "Tu mensaje se está enviando. Por favor, espera un momento.";
    }
)}
);
