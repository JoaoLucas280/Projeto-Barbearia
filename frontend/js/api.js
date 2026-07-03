const BASE_URL = "http://localhost:8080";

async function buscarServicos() {
    const response = await fetch(`${BASE_URL}/api/servicos/v1`);
    const data = await response.json();
    return data;
}

async function buscarHorariosDisponiveis(data, servicoId) {
    const response = await fetch(`${BASE_URL}/api/agendamentos/v1/horarios-disponiveis?data=${data}&servicoid=${servicoId}`);
    const horarios = await response.json();
    return horarios;
}

async function criarAgendamento(agendamento) {
    const response = await fetch(`${BASE_URL}/api/agendamentos/v1`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(agendamento)
    });
    return response;
}

async function buscarAgendamentosPorEmail(email) {
    const response = await fetch(`${BASE_URL}/api/agendamentos/v1?email=${email}`);
    const data = await response.json();
    return data;
}

async function cancelarAgendamento(id, email) {
    const response = await fetch(`${BASE_URL}/api/agendamentos/v1/${id}/cancelar?email=${email}`, {
        method: "PATCH"
    });
    return response;
}

async function login(username, senha) {
    const response = await fetch(`${BASE_URL}/api/auth/v1/login`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ username, senha })
    });
    return response;
}

async function buscarTodosAgendamentos() {
    const token = sessionStorage.getItem("token");
    const response = await fetch(`${BASE_URL}/api/agendamentos/v1/todos`, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    });
    const data = await response.json();
    return data;
}

async function buscarServicoPorId(id) {
    const token = sessionStorage.getItem("token");
    const response = await fetch(`${BASE_URL}/api/servicos/v1/${id}`, {
        headers: { "Authorization": `Bearer ${token}` }
    });
    return await response.json();
}

async function criarServico(servico) {
    const token = sessionStorage.getItem("token");
    const response = await fetch(`${BASE_URL}/api/servicos/v1`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify(servico)
    });
    return response;
}

async function atualizarServico(id, servico) {
    const token = sessionStorage.getItem("token");
    const response = await fetch(`${BASE_URL}/api/servicos/v1/${id}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify(servico)
    });
    return response;
}

async function deletarServico(id) {
    const token = sessionStorage.getItem("token");
    const response = await fetch(`${BASE_URL}/api/servicos/v1/${id}`, {
        method: "DELETE",
        headers: { "Authorization": `Bearer ${token}` }
    });
    return response;
}