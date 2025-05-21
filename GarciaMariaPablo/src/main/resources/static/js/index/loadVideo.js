document.addEventListener('DOMContentLoaded', function () {
    const links = document.querySelectorAll('.list-group-item[data-video]');
    const loader = document.getElementById('video-loader');
    const container = document.getElementById('video-container');
    const iframe = document.getElementById('demoVideo');
    const demoOffcanvas = document.getElementById('demoOffcanvas');
    let defaultVideo = 'https://www.youtube.com/embed/d1OoUeSv-Ng?si=T2q-aWl0i6L2-ti4';

    function showLoader(videoUrl) {
        iframe.src = '';
        container.classList.add('d-none');
        loader.classList.remove('d-none');
        links.forEach(link => {
            link.classList.add('disabled');
        })
        currentTimeout = setTimeout(function() {
            hideLoader(videoUrl)
        },750)
    }

    function hideLoader(videoUrl) {
        iframe.src = videoUrl;
        loader.classList.add('d-none');
        container.classList.remove('d-none');
        links.forEach(link => {
            link.classList.remove('disabled');
        })

    }

    demoOffcanvas.addEventListener('shown.bs.offcanvas', () => {
        showLoader(defaultVideo)
    });

    demoOffcanvas.addEventListener('hidden.bs.offcanvas', () => {
        iframe.src = '';
        loader.classList.remove('d-none');
        container.classList.add('d-none');
    });

    links.forEach(link => {
        link.addEventListener('click', function (e) {
            e.preventDefault();
            const videoUrl = this.getAttribute('data-video');

            showLoader(videoUrl)


        });
    });
});

