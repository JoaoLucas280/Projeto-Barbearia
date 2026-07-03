document.querySelector('a[href="barbeiro.html"]').classList.add("ativo");

document.addEventListener("DOMContentLoaded", async () => {
    const token = sessionStorage.getItem("token");
    if (!token) {
        window.location.href = "login.html";
        return;
    }

    const form = document.getElementById("form-barbeiro");
    const mensagem = document.getElementById("mensagem");

    document.getElementById("btn-logout").addEventListener("click", () => {
        sessionStorage.removeItem("token");
        window.location.href = "login.html";
    });

    // Carrega dados atuais do barbeiro
    const barbeiro = await buscarBarbeiro();

    document.getElementById("nome").value = barbeiro.nome || "";
    document.getElementById("especialidade").value = barbeiro.especialidade || "";
    document.getElementById("descricao").value = barbeiro.descricao || "";
    document.getElementById("horarioInicio").value = barbeiro.horarioInicio || "";
    document.getElementById("horarioFim").value = barbeiro.horarioFim || "";

    // Marca os dias de trabalho atuais
    document.querySelectorAll("input[type=checkbox]").forEach(checkbox => {
        if (barbeiro.diasTrabalho && barbeiro.diasTrabalho.includes(checkbox.value)) {
            checkbox.checked = true;
        }
    });

    // Atualiza barbeiro
    form.addEventListener("submit", async (event) => {
        event.preventDefault();

        const diasSelecionados = [];
        document.querySelectorAll("input[type=checkbox]:checked").forEach(cb => {
            diasSelecionados.push(cb.value);
        });

        const barbeirAtualizado = {
            nome: document.getElementById("nome").value,
            especialidade: document.getElementById("especialidade").value,
            descricao: document.getElementById("descricao").value,
            horarioInicio: document.getElementById("horarioInicio").value,
            horarioFim: document.getElementById("horarioFim").value,
            diasTrabalho: diasSelecionados
        };

        const response = await atualizarBarbeiro(barbeirAtualizado);

        if (response.ok) {
            mensagem.textContent = "Perfil atualizado com sucesso!";
            mensagem.style.color = "green";
        } else {
            mensagem.textContent = "Erro ao atualizar perfil.";
            mensagem.style.color = "red";
        }
    });
});