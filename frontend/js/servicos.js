document.querySelector('a[href="servicos.html"]').classList.add("ativo");

document.addEventListener("DOMContentLoaded", async () => {
    const token = sessionStorage.getItem("token");
    if (!token) {
        window.location.href = "login.html";
        return;
    }

    const form = document.getElementById("form-servico");
    const btnSalvar = document.getElementById("btn-salvar");
    const btnCancelar = document.getElementById("btn-cancelar");
    const mensagem = document.getElementById("mensagem");

    document.getElementById("btn-logout").addEventListener("click", () => {
        sessionStorage.removeItem("token");
        window.location.href = "login.html";
    });

    async function carregarServicos() {
        const servicos = await buscarServicos();
        const corpo = document.getElementById("corpo-tabela");
        corpo.innerHTML = "";

        servicos.forEach(s => {
            const linha = document.createElement("tr");
            linha.innerHTML = `
                <td>${s.nome}</td>
                <td>R$ ${s.valor}</td>
                <td>${s.duracao} min</td>
                <td>${s.descricao || "-"}</td>
                <td>
                    <button class="btn-editar" data-id="${s.id}">Editar</button>
                    <button class="btn-deletar" data-id="${s.id}">Deletar</button>
                </td>
            `;
            corpo.appendChild(linha);
        });

        // Eventos de editar
        document.querySelectorAll(".btn-editar").forEach(btn => {
            btn.addEventListener("click", async () => {
                const servico = await buscarServicoPorId(btn.dataset.id);
                document.getElementById("servico-id").value = servico.id;
                document.getElementById("nome").value = servico.nome;
                document.getElementById("valor").value = servico.valor;
                document.getElementById("duracao").value = servico.duracao;
                document.getElementById("descricao").value = servico.descricao || "";
                btnSalvar.textContent = "Atualizar";
                btnCancelar.style.display = "inline";
            });
        });

        // Eventos de deletar
        document.querySelectorAll(".btn-deletar").forEach(btn => {
            btn.addEventListener("click", async () => {
                const response = await deletarServico(btn.dataset.id);
                if (response.ok) {
                    mensagem.textContent = "Serviço deletado com sucesso!";
                    mensagem.style.color = "green";
                    await carregarServicos();
                } else {
                    mensagem.textContent = "Erro ao deletar serviço.";
                    mensagem.style.color = "red";
                }
            });
        });
    }

    // Cancelar edição
    btnCancelar.addEventListener("click", () => {
        form.reset();
        document.getElementById("servico-id").value = "";
        btnSalvar.textContent = "Salvar";
        btnCancelar.style.display = "none";
    });

    // Salvar ou atualizar
    form.addEventListener("submit", async (event) => {
        event.preventDefault();

        const id = document.getElementById("servico-id").value;
        const servico = {
            nome: document.getElementById("nome").value,
            valor: document.getElementById("valor").value,
            duracao: document.getElementById("duracao").value,
            descricao: document.getElementById("descricao").value
        };

        const response = id
            ? await atualizarServico(id, servico)
            : await criarServico(servico);

        if (response.ok) {
            mensagem.textContent = id ? "Serviço atualizado!" : "Serviço criado!";
            mensagem.style.color = "green";
            form.reset();
            document.getElementById("servico-id").value = "";
            btnSalvar.textContent = "Salvar";
            btnCancelar.style.display = "none";
            await carregarServicos();
        } else {
            mensagem.textContent = "Erro ao salvar serviço.";
            mensagem.style.color = "red";
        }
    });

    await carregarServicos();
});