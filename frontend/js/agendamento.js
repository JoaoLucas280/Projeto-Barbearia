document.addEventListener("DOMContentLoaded", async () => {
    const selectServico = document.getElementById("servico");
    const inputData = document.getElementById("data");
    const selectHorario = document.getElementById("horario");
    const form = document.getElementById("form-agendamento");
    const mensagem = document.getElementById("mensagem");

    // 1. Carrega os serviços quando a página abre
    const servicos = await buscarServicos();
    servicos.forEach(servico => {
        const option = document.createElement("option");
        option.value = servico.id;
        option.textContent = `${servico.nome} - R$ ${servico.valor}`;
        selectServico.appendChild(option);
    });

    // 2. Habilita a data quando o serviço é escolhido
    selectServico.addEventListener("change", () => {
        if (selectServico.value) {
            inputData.disabled = false;
        } else {
            inputData.disabled = true;
            selectHorario.disabled = true;
        }
    });

    // 3. Busca horários quando a data é escolhida
    inputData.addEventListener("change", async () => {
        const servicoId = selectServico.value;
        const data = inputData.value;

        selectHorario.innerHTML = "<option value=''>Selecione um horário</option>";
        const horarios = await buscarHorariosDisponiveis(data, servicoId);

        horarios.forEach(horario => {
            const option = document.createElement("option");
            option.value = horario;
            option.textContent = horario;
            selectHorario.appendChild(option);
        });

        selectHorario.disabled = false;
    });

    // 4. Envia o agendamento
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

        const response = await criarAgendamento(agendamento);

        if (response.ok) {
            mensagem.textContent = "Agendamento realizado com sucesso!";
            mensagem.style.color = "green";
            form.reset();
        } else {
            const erro = await response.json();
            mensagem.textContent = erro.message || "Erro ao realizar agendamento.";
            mensagem.style.color = "red";
        }
    });
});