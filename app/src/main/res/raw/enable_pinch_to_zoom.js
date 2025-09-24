// Zoom Disable Script
(function() {
    function applyViewportLock() {
        let viewport = document.querySelector('meta[name="viewport"]');
        if (!viewport) {
            viewport = document.createElement('meta');
            viewport.name = "viewport";
            document.head.appendChild(viewport);
        }
        // allow zooming
        viewport.content = "width=device-width, initial-scale=1.0";
    }

    applyViewportLock();

    const observer = new MutationObserver(() => {
        applyViewportLock();
    });

    observer.observe(document.head || document.documentElement, {
        childList: true,
        subtree: true
    });
})();