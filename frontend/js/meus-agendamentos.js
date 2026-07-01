document.addEventListener("DOMContentLoaded", () => {
    const btnBuscar = document.getElementById("btn-buscar");
    const inputEmail = document.getElementById("email");
    const lista = document.getElementById("lista-agendamentos");
    const mensagem = document.getElementById("mensagem");

    btnBuscar.addEventListener("click", async () => {
        const email = inputEmail.value;

        if (!email) {
            mensagem.textContent = "Digite seu email para buscar.";
            return;
        }

        const agendamentos = await buscarAgendamentosPorEmail(email);

        if (!agendamentos || agendamentos.length === 0) {
            lista.innerHTML = "";
            mensagem.textContent = "Nenhum agendamento encontrado para esse email.";
            return;
        }

        mensagem.textContent = "";
        lista.innerHTML = "";

        agendamentos.forEach(ag => {
            const card = document.createElement("div");
            card.className = `card ${ag.status.toLowerCase()}`;

            let icone = "";
            let botaoCancelar = "";

            if (ag.status === "AGENDADO") {
                icone = "⏰";
                botaoCancelar = `<button class="btn-cancelar" data-id="${ag.id}" data-email="${email}">Cancelar</button>`;
            } else if (ag.status === "CONCLUIDO") {
                icone = "✅";
            } else if (ag.status === "CANCELADO") {
                icone = "❌";
            }

            card.innerHTML = `
                <p>${icone} <strong>Serviço ID:</strong> ${ag.servicoId}</p>
                <p><strong>Data:</strong> ${ag.data}</p>
                <p><strong>Horário:</strong> ${ag.horarioInicio} - ${ag.horarioFim}</p>
                <p><strong>Status:</strong> ${ag.status}</p>
                ${botaoCancelar}
            `;

            lista.appendChild(card);
        });

        document.querySelectorAll(".btn-cancelar").forEach(btn => {
            btn.addEventListener("click", async () => {
                const id = btn.dataset.id;
                const email = btn.dataset.email;

                const response = await cancelarAgendamento(id, email);

                if (response.ok) {
                    mensagem.textContent = "Agendamento cancelado com sucesso!";
                    btnBuscar.click();
                } else {
                    mensagem.textContent = "Erro ao cancelar agendamento.";
                }
            });
        });
    });
});