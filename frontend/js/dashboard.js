document.addEventListener("DOMContentLoaded", async () => {
    const token = sessionStorage.getItem("token");
    if (!token) {
        window.location.href = "login.html";
        return;
    }

    const btnLogout = document.getElementById("btn-logout");
    const corpoTabela = document.getElementById("corpo-tabela");
    const mensagem = document.getElementById("mensagem");

    btnLogout.addEventListener("click", () => {
        sessionStorage.removeItem("token");
        window.location.href = "login.html";
    });

    // Carrega serviços e agendamentos em paralelo
    const [agendamentos, servicos] = await Promise.all([
        buscarTodosAgendamentos(),
        buscarServicos()
    ]);

    // Monta mapa id → nome
    const mapaServicos = {};
    servicos.forEach(s => {
        mapaServicos[s.id] = s.nome;
    });

    if (!agendamentos || agendamentos.length === 0) {
        mensagem.textContent = "Nenhum agendamento encontrado.";
        return;
    }

    agendamentos.forEach(ag => {
        const linha = document.createElement("tr");
        linha.innerHTML = `
            <td>${ag.clienteNome}</td>
            <td>${ag.clienteEmail}</td>
            <td>${mapaServicos[ag.servicoId] || ag.servicoId}</td>
            <td>${ag.data}</td>
            <td>${ag.horarioInicio} - ${ag.horarioFim}</td>
            <td>${ag.status}</td>
        `;
        corpoTabela.appendChild(linha);
    });
});