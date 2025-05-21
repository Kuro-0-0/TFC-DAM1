document.addEventListener('DOMContentLoaded', function () {
    // Función para buscar en FAQs
    document.getElementById('searchButton').addEventListener('click', function () {
        const searchTerm = document.getElementById('faqSearch').value.toLowerCase();
        const accordionItems = document.querySelectorAll('.accordion-item');

        accordionItems.forEach(item => {
            const question = item.querySelector('.accordion-button').textContent.toLowerCase();
            const answer = item.querySelector('.accordion-body').textContent.toLowerCase();

            if (question.includes(searchTerm) || answer.includes(searchTerm)) {
                item.style.display = 'block';
            } else {
                item.style.display = 'none';
            }
        });
    });

    // Permitir búsqueda con Enter
    document.getElementById('faqSearch').addEventListener('keypress', function (e) {
        if (e.key === 'Enter') {
            document.getElementById('searchButton').click();
        }
    });
});
