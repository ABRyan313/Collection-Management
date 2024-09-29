function toggleTheme() {
    let currentTheme = document.body.classList.contains('dark-theme') ? 'dark' : 'light';
    let newTheme = currentTheme === 'dark' ? 'light' : 'dark';
    
    document.body.classList.toggle('dark-theme');
    document.body.classList.toggle('light-theme');

    // Store the theme in localStorage
    localStorage.setItem('theme', newTheme);

    // Submit the change to the server to persist the theme across pages
    fetch(`/setTheme?theme=${newTheme}`, { method: 'POST' });
}

// On page load, apply the saved theme
document.addEventListener('DOMContentLoaded', (event) => {
    const savedTheme = localStorage.getItem('theme') || 'light';
    document.body.classList.add(savedTheme + '-theme');
});
