document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("form-login");
    const mensagem = document.getElementById("mensagem");

    form.addEventListener("submit", async (event) => {
        event.preventDefault();

        const username = document.getElementById("username").value;
        const senha = document.getElementById("senha").value;

        const response = await login(username, senha);

        if (response.ok) {
            const data = await response.json();
            sessionStorage.setItem("token", data.token);
            window.location.href = "dashboard.html";
        } else {
            mensagem.textContent = "Usuário ou senha inválidos.";
            mensagem.style.color = "red";
        }
    });
});