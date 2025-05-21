document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("formMail").addEventListener("submit", function (event) {
        const modalElement = document.getElementById('contactModal');
        const modalInstance = bootstrap.Modal.getInstance(modalElement) || new bootstrap.Modal(modalElement);
        modalInstance.hide();
        setTimeout(() => {
            document.getElementById("infoMessage").classList.remove("d-none");
            document.getElementById("infoMessage").scrollIntoView({ behavior: "smooth" });
            const infoText = document.getElementById("infoTextMessage");
            infoText.textContent = "Tu mensaje se está enviando. Por favor, espera un momento.";
        }, 1000);

    }
    )
}
);
