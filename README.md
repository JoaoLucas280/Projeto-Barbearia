# 💈 Sistema de Agendamento de Barbearia
 
Sistema web completo para gerenciamento de agendamentos de barbearia, desenvolvido do zero com Java 21 + Spring Boot no backend e HTML/CSS/JavaScript puro no frontend.
 
---
 
## 📸 Screenshots
 
### Área Pública — Agendamento
<img width="1613" height="896" alt="image" src="https://github.com/user-attachments/assets/71695ed9-92a4-42f5-baed-2db0a2cb66cc" />

### Área Pública — Meus Agendamentos
<img width="1577" height="658" alt="image" src="https://github.com/user-attachments/assets/a2844552-ec8c-478c-989d-8b3f81771d9c" />

### Área Admin — Dashboard
<img width="1888" height="621" alt="image" src="https://github.com/user-attachments/assets/d27a9141-ee9c-4b1f-ab62-b6163a51421c" />

### Área Admin — Serviços
<img width="1877" height="672" alt="image" src="https://github.com/user-attachments/assets/33f8b0ab-fb32-4865-9ab6-2bcc1fa22f90" />
 
---
 
## 🚀 Tecnologias
 
### Backend
- **Java 21** (LTS)
- **Spring Boot 4.1.0**
- **Spring Security** com autenticação JWT stateless
- **Spring Data JPA** + **Hibernate**
- **MySQL 8.4**
- **Flyway** para versionamento do banco de dados
- **springdoc-openapi 3.0.2** para documentação da API (Swagger UI)
- **Dozer** para mapeamento de objetos
- **JavaMailSender** para email transacional
- **JUnit 5** + **Mockito** para testes unitários
### Frontend
- **HTML5**, **CSS3** e **JavaScript** puro (sem frameworks)
- Integração com a API REST via `fetch()`
- Autenticação JWT via `sessionStorage`
### Infraestrutura
- **Docker Compose** com MySQL e Mailpit
- **Git Flow** para controle de versão
  
---
 
## 📋 Funcionalidades
 
### Área Pública (Cliente)
- Agendamento de horário com seleção de serviço, data e horário disponível
- Horários disponíveis calculados dinamicamente com base na duração do serviço e agenda do barbeiro
- Validação de dias de atendimento diretamente no frontend
- Consulta de agendamentos por email
- Cancelamento de agendamento
- Email de confirmação e cancelamento automático
### Área Administrativa (Barbeiro)
- Login com autenticação JWT
- Dashboard com todos os agendamentos
- CRUD completo de serviços
- Edição de perfil (nome, especialidade, horários e dias de trabalho)
- Gerenciamento de clientes (consulta, edição e remoção com validação de agendamentos ativos)
---
 
## 🏗️ Arquitetura
 
```
src/
├── main/
│   ├── java/com/joaolucas/sistema/barbearia/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── entity/
│   │   │   └── enums/
│   │   ├── exception/
│   │   ├── mapper/
│   │   ├── repository/
│   │   ├── security/
│   │   └── service/
│   └── resources/
│       └── db/migration/    ← Migrations do Flyway
├── test/
│   └── java/                ← Testes unitários
frontend/
├── public/                  ← Área do cliente
├── admin/                   ← Área administrativa
├── js/                      ← Lógica JavaScript
└── css/                     ← Estilos
```
 
---
 
## ⚙️ Como rodar o projeto
 
### Pré-requisitos
- Java 21
- Maven
- Docker e Docker Compose
### 1. Clone o repositório
```bash
git clone https://github.com/JoaoLucas280/Projeto-Barbearia.git
cd Projeto-Barbearia
```
 
### 2. Configure as variáveis de ambiente
Copie o arquivo de exemplo e preencha os valores:
```bash
cp .env.example .env
```
 
Edite o `.env` com suas configurações:
```
MYSQL_ROOT_PASSWORD=sua_senha_root
MYSQL_DATABASE=barbearia
```
 
### 3. Configure as variáveis no ambiente de execução
A aplicação precisa de duas variáveis de ambiente configuradas diretamente no ambiente de execução (não no `.env`):
 
```
MYSQL_PASSWORD=senha_do_usuario_barbearia_app
JWT_SECRET=seu_segredo_jwt_aqui
```
 
### 4. Suba os containers
```bash
docker-compose up -d
```
 
Isso sobe:
- **MySQL 8.4** na porta `3306`
- **Mailpit** na porta `1025` (SMTP) e `8025` (UI web)
### 5. Crie o usuário do banco de dados
Conecte no MySQL como root e execute:
```sql
CREATE USER 'barbearia_app'@'%' IDENTIFIED BY 'sua_senha';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, ALTER, INDEX, REFERENCES ON barbearia.* TO 'barbearia_app'@'%';
FLUSH PRIVILEGES;
```
 
### 6. Rode a aplicação
```bash
./mvnw spring-boot:run
```
 
O Flyway vai criar as tabelas automaticamente na primeira execução.
 
### 7. Primeiro setup do sistema
Após subir a aplicação, configure o sistema pela primeira vez:
 
1. Acesse o Swagger UI: `http://localhost:8080/swagger-ui/index.html`
2. Crie o usuário admin via `POST /api/usuarios/v1`
3. Faça login via `POST /api/auth/v1/login` e copie o token
4. Clique em **Authorize** no Swagger e cole o token
5. Cadastre o barbeiro via `POST /api/barbeiro/v1`
6. Cadastre os serviços via `POST /api/servicos/v1`
### 8. Acesse o frontend
Abra o arquivo `frontend/public/index.html` no navegador.
 
Para a área administrativa, acesse `frontend/admin/login.html`.
 
---
 
## 📧 Email transacional
 
Em desenvolvimento, os emails são capturados pelo **Mailpit** e podem ser visualizados em `http://localhost:8025`.
 
Para produção, configure um servidor SMTP real (ex: Brevo) nas variáveis de ambiente de email do `application.yml`.
 
---
 
## 🧪 Testes
 
Execute os testes unitários com:
```bash
./mvnw test
```
 
Os testes cobrem os services: `AgendamentoService`, `ClienteService`, `BarbeiroService` e `ServicoService`.
 
---
 
## 📖 Documentação da API
 
Com a aplicação rodando, acesse:
```
http://localhost:8080/swagger-ui/index.html
```
 
---
 
## 🔐 Segurança
 
- Autenticação via **JWT stateless** (sem sessão no servidor)
- Senhas armazenadas com **BCrypt**
- Rotas administrativas protegidas por token
- Usuário de banco de dados com permissões mínimas (sem acesso root)
- CORS configurado
---
 
## 📁 Variáveis de ambiente
 
| Variável | Descrição |
|---|---|
| `MYSQL_ROOT_PASSWORD` | Senha do root do MySQL (usado pelo Docker Compose) |
| `MYSQL_DATABASE` | Nome do banco de dados |
| `MYSQL_PASSWORD` | Senha do usuário `barbearia_app` |
| `JWT_SECRET` | Chave secreta para assinatura dos tokens JWT |
 
---
 
## 👨‍💻 Autor
 
**João Lucas**
 
[![GitHub](https://img.shields.io/badge/GitHub-JoaoLucas280-181717?style=flat&logo=github)](https://github.com/JoaoLucas280)

---

## Licença
- Este projeto está licenciado sob a Licença MIT. Consulte o arquivo `LICENSE` na raiz do repositório para detalhes.

- Copyright © 2026 Joao Lucas
