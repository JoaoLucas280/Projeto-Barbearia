document.addEventListener("DOMContentLoaded", async () => {
    const token = sessionStorage.getItem("token");
    if (!token) {
        window.location.href = "login.html";
        return;
    }

    const formEdicao = document.getElementById("form-edicao");
    const mensagem = document.getElementById("mensagem");

    document.getElementById("btn-logout").addEventListener("click", () => {
        sessionStorage.removeItem("token");
        window.location.href = "login.html";
    });

    async function carregarClientes() {
        const clientes = await buscarClientes();
        const corpo = document.getElementById("corpo-tabela");
        corpo.innerHTML = "";

        clientes.forEach(c => {
            const linha = document.createElement("tr");
            linha.innerHTML = `
                <td>${c.nome}</td>
                <td>${c.email}</td>
                <td>${c.telefone}</td>
                <td>
                    <button class="btn-editar" 
                        data-id="${c.id}" 
                        data-nome="${c.nome}" 
                        data-email="${c.email}" 
                        data-telefone="${c.telefone}">Editar</button>
                    <button class="btn-deletar" data-id="${c.id}">Deletar</button>
                </td>
            `;
            corpo.appendChild(linha);
        });

        // Eventos de editar
        document.querySelectorAll(".btn-editar").forEach(btn => {
            btn.addEventListener("click", () => {
                document.getElementById("cliente-id").value = btn.dataset.id;
                document.getElementById("nome").value = btn.dataset.nome;
                document.getElementById("email").value = btn.dataset.email;
                document.getElementById("telefone").value = btn.dataset.telefone;
                formEdicao.style.display = "block";
            });
        });

        // Eventos de deletar
        document.querySelectorAll(".btn-deletar").forEach(btn => {
            btn.addEventListener("click", async () => {
                const response = await deletarCliente(btn.dataset.id);
                if (response.ok) {
                    mensagem.textContent = "Cliente removido com sucesso!";
                    mensagem.style.color = "green";
                    await carregarClientes();
                } else {
                    mensagem.textContent = "Erro ao remover cliente.";
                    mensagem.style.color = "red";
                }
            });
        });
    }

    // Cancelar edição
    document.getElementById("btn-cancelar").addEventListener("click", () => {
        formEdicao.style.display = "none";
        document.getElementById("cliente-id").value = "";
    });

    // Salvar edição
    document.getElementById("btn-salvar").addEventListener("click", async () => {
        const id = document.getElementById("cliente-id").value;
        const cliente = {
            nome: document.getElementById("nome").value,
            email: document.getElementById("email").value,
            telefone: document.getElementById("telefone").value
        };

        const response = await atualizarCliente(id, cliente);

        if (response.ok) {
            mensagem.textContent = "Cliente atualizado com sucesso!";
            mensagem.style.color = "green";
            formEdicao.style.display = "none";
            await carregarClientes();
        } else {
            mensagem.textContent = "Erro ao atualizar cliente.";
            mensagem.style.color = "red";
        }
    });

    await carregarClientes();
});