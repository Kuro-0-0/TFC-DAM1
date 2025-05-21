document.addEventListener('DOMContentLoaded', function () {
    const links = document.querySelectorAll('.list-group-item[data-video]');
    const loader = document.getElementById('video-loader');
    const container = document.getElementById('video-container');
    const iframe = document.getElementById('demoVideo');
    const demoOffcanvas = document.getElementById('demoOffcanvas');
    let defaultVideo = 'https://www.youtube.com/embed/d1OoUeSv-Ng?si=T2q-aWl0i6L2-ti4';

    demoOffcanvas.addEventListener('shown.bs.offcanvas', () => {
        iframe.src = '';
        container.classList.add('d-none');
        loader.classList.remove('d-none');

        currentTimeout = setTimeout(() => {
            iframe.src = defaultVideo;
            loader.classList.add('d-none');
            container.classList.remove('d-none');
        }, 3400);
    });

    demoOffcanvas.addEventListener('hidden.bs.offcanvas', () => {
        iframe.src = '';
        loader.classList.remove('d-none');
        container.classList.add('d-none');
    });

    links.forEach(link => {
        link.addEventListener('click', function (e) {
            e.preventDefault();

            iframe.src = '';
            container.classList.add('d-none');
            loader.classList.remove('d-none');

            const videoUrl = this.getAttribute('data-video');
            iframe.src = videoUrl;
            loader.classList.add('d-none');
            container.classList.remove('d-none');
        });
    });
});