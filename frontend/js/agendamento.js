document.addEventListener("DOMContentLoaded", async () => {
    const selectServico = document.getElementById("servico");
    const inputData = document.getElementById("data");
    const selectHorario = document.getElementById("horario");
    const form = document.getElementById("form-agendamento");
    const mensagem = document.getElementById("mensagem");

    const diasSemana = {
        0: "SUNDAY",
        1: "MONDAY",
        2: "TUESDAY",
        3: "WEDNESDAY",
        4: "THURSDAY",
        5: "FRIDAY",
        6: "SATURDAY"
    };

    const diasPortugues = {
        "MONDAY": "Segunda",
        "TUESDAY": "Terça",
        "WEDNESDAY": "Quarta",
        "THURSDAY": "Quinta",
        "FRIDAY": "Sexta",
        "SATURDAY": "Sábado",
        "SUNDAY": "Domingo"
    };

    selectServico.innerHTML = "<option value=''>Carregando serviços...</option>";

    const [servicos, barbeiro] = await Promise.all([
        buscarServicos(),
        buscarBarbeiro()
    ]);
    const diasDisponiveis = barbeiro.diasTrabalho || [];

    selectServico.innerHTML = "<option value=''>Selecione um serviço</option>";

    servicos.forEach(servico => {
        const option = document.createElement("option");
        option.value = servico.id;
        option.textContent = `${servico.nome} - R$ ${servico.valor}`;
        selectServico.appendChild(option);
    });

    selectServico.addEventListener("change", () => {
        if (selectServico.value) {
            inputData.disabled = false;
            selectHorario.disabled = true;
            selectHorario.innerHTML = "<option value=''>Selecione um horário</option>";
            mensagem.textContent = "";
        } else {
            inputData.disabled = true;
            selectHorario.disabled = true;
        }
    });

    inputData.addEventListener("change", async () => {
        const dataSelecionada = new Date(inputData.value + "T00:00:00");
        const diaSemanaJS = dataSelecionada.getDay();
        const diaSemanaJava = diasSemana[diaSemanaJS];

        selectHorario.innerHTML = "<option value=''>Selecione um horário</option>";
        selectHorario.disabled = true;
        mensagem.textContent = "";

        if (!diasDisponiveis.includes(diaSemanaJava)) {
            const nomesDisponiveis = diasDisponiveis
                .map(d => diasPortugues[d])
                .join(", ");
            mensagem.textContent = `O barbeiro não atende nesse dia. Dias disponíveis: ${nomesDisponiveis}.`;
            mensagem.style.color = "#aaaaaa";
            return;
        }

        selectHorario.innerHTML = "<option value=''>Carregando horários...</option>";
        const horarios = await buscarHorariosDisponiveis(inputData.value, selectServico.value);
        selectHorario.innerHTML = "<option value=''>Selecione um horário</option>";

        if (!horarios || horarios.length === 0) {
            mensagem.textContent = "Não há horários disponíveis nesse dia.";
            mensagem.style.color = "#aaaaaa";
            return;
        }

        horarios.forEach(horario => {
            const option = document.createElement("option");
            option.value = horario;
            option.textContent = horario;
            selectHorario.appendChild(option);
        });

        selectHorario.disabled = false;
    });

    form.addEventListener("submit", async (event) => {
        event.preventDefault();

        const agendamento = {
            clienteNome: document.getElementById("nome").value,
            clienteEmail: document.getElementById("email").value,
            clienteTelefone: document.getElementById("telefone").value,
            servicoId: selectServico.value,
            data: inputData.value,
            horarioInicio: selectHorario.value
        };

        const btnAgendar = form.querySelector("button[type='submit']");
        btnAgendar.textContent = "Aguarde...";
        btnAgendar.disabled = true;


        const response = await criarAgendamento(agendamento);

        if (response.ok) {
            mensagem.innerHTML = `
                Agendamento realizado com sucesso! 
                btnAgendar.textContent = "Agendar";
                btnAgendar.disabled = false;
                <a href="meus-agendamentos.html?email=${document.getElementById('email').value}">
                    Ver meus agendamentos
                </a>
            `;
            mensagem.style.color = "green";
            form.reset();
            selectHorario.disabled = true;
            inputData.disabled = true;
        } else {
            btnAgendar.textContent = "Agendar";
            btnAgendar.disabled = false;
            const erro = await response.json();
            mensagem.textContent = erro.message || "Erro ao realizar agendamento.";
            mensagem.style.color = "red";
        }
    });
});