# Sistema Barbearia

API REST para gerenciamento de uma barbearia: clientes, barbeiros, serviços, agendamentos e autenticação.

## Principais funcionalidades
- CRUD de Clientes, Barbeiros, Serviços e Agendamentos
- Autenticação via JWT
- Envio de e-mails (configurável)
- Migrações de banco com Flyway
- Documentação OpenAPI (Swagger)

## Stack
- Java 17
- Spring Boot 4.1.0
- Maven
- MySQL (produção) / H2 (runtime/testes)
- Flyway, Spring Security, jjwt, Lombok, Dozer, Springdoc OpenAPI

## Pré-requisitos
- Java 17
- Maven
- MySQL (ou usar H2 para desenvolvimento)
- Opcional: Mailhog ou outro SMTP para testar envio de e-mail

## Configuração
- Copie/adeque `src/main/resources/application-example.yml` para `application.yml` (ou configure variáveis de ambiente).
- Variáveis importantes (exemplos):
  - MYSQL_PASSWORD: senha do usuário `barbearia_app`
  - JWT_SECRET: segredo para geração de tokens JWT

O arquivo `src/main/resources/application.yml` já contém a URL padrão do banco:
`jdbc:mysql://localhost:3306/barbearia`

## Banco de Dados
- Migrações Flyway estão em `src/main/resources/db/migration` (ex.: `V1__criar_tabelas.sql`).
- Em dev, pode-se usar H2; em produção configure MySQL conforme `application.yml`.

## Como executar
Modo desenvolvimento (com Spring Boot DevTools):

- Usando Maven:
  mvn spring-boot:run

Gerar jar e executar:

  mvn clean package
  java -jar target/Sistema.Barbearia-0.0.1-SNAPSHOT.jar

A aplicação por padrão fica em `http://localhost:8080`.

## Testes
- Rodar testes com:
  mvn test

## Documentação da API
- Springdoc OpenAPI está configurado; a UI do Swagger normalmente fica em:
  `http://localhost:8080/swagger-ui/index.html` (ou `swagger-ui.html`)

## Boas práticas
- Não comitar segredos (ex.: JWT_SECRET, senhas)
- Use profiles (dev/prod/test) para separar configurações

## Contribuição
- Abra issues para bugs/feature requests
- Envie PRs com descrições claras e testes quando aplicável

## Licença
- Este projeto está licenciado sob a Licença MIT. Consulte o arquivo `LICENSE` na raiz do repositório para detalhes.

- Copyright © 2026 Joao Lucas
