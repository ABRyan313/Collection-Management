document.addEventListener('DOMContentLoaded', function() {
    const languageSelector = document.querySelector('.language-switcher select');
    languageSelector.addEventListener('change', function() {
        this.form.submit();
    });
});
