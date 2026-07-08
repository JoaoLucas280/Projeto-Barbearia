CREATE TABLE clientes (
                          id BIGINT NOT NULL AUTO_INCREMENT,
                          email VARCHAR(255) NOT NULL,
                          nome VARCHAR(255) NOT NULL,
                          telefone VARCHAR(255) NOT NULL,
                          PRIMARY KEY (id)
);

CREATE TABLE barbeiros (
                           id BIGINT NOT NULL AUTO_INCREMENT,
                           descricao VARCHAR(255) DEFAULT NULL,
                           especialidade VARCHAR(255) NOT NULL,
                           horario_fim TIME DEFAULT NULL,
                           horario_inicio TIME DEFAULT NULL,
                           nome VARCHAR(255) NOT NULL,
                           PRIMARY KEY (id)
);

CREATE TABLE barbeiro_dias_disponiveis (
                                           barbeiro_id BIGINT NOT NULL,
                                           dia ENUM('FRIDAY','MONDAY','SATURDAY','SUNDAY','THURSDAY','TUESDAY','WEDNESDAY') DEFAULT NULL,
                                           UNIQUE KEY UK8k9mdqbhrnvigw14scqqby3xd (barbeiro_id, dia),
                                           CONSTRAINT FKkrpxlkpb5y8cgih4auk56dbiy FOREIGN KEY (barbeiro_id) REFERENCES barbeiros (id)
);

CREATE TABLE servicos (
                          id BIGINT NOT NULL AUTO_INCREMENT,
                          descricao VARCHAR(255) DEFAULT NULL,
                          duracao INT NOT NULL,
                          nome VARCHAR(255) NOT NULL,
                          valor DECIMAL(38,2) NOT NULL,
                          PRIMARY KEY (id)
);

CREATE TABLE agendamentos (
                              id BIGINT NOT NULL AUTO_INCREMENT,
                              data DATE NOT NULL,
                              horario_fim TIME NOT NULL,
                              horario_inicio TIME NOT NULL,
                              status ENUM('AGENDADO','CANCELADO','CONCLUIDO') NOT NULL,
                              barbeiro_id BIGINT NOT NULL,
                              cliente_id BIGINT NOT NULL,
                              servico_id BIGINT NOT NULL,
                              PRIMARY KEY (id),
                              CONSTRAINT FK_agendamentos_barbeiro FOREIGN KEY (barbeiro_id) REFERENCES barbeiros (id),
                              CONSTRAINT FK_agendamentos_cliente FOREIGN KEY (cliente_id) REFERENCES clientes (id),
                              CONSTRAINT FK_agendamentos_servico FOREIGN KEY (servico_id) REFERENCES servicos (id)
);

CREATE TABLE usuario (
                         id BIGINT NOT NULL AUTO_INCREMENT,
                         role ENUM('ADMIN') DEFAULT NULL,
                         senha VARCHAR(255) NOT NULL,
                         username VARCHAR(255) NOT NULL,
                         PRIMARY KEY (id)
);