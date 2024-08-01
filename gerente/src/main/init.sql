CREATE SCHEMA gerente;

CREATE TABLE gerente.gerentes (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    cpf VARCHAR(11) UNIQUE NOT NULL,
    telefone VARCHAR(20)
);

INSERT INTO gerente.gerentes (nome, email, cpf, telefone) VALUES
('João da Silva', 'joao.silva@example.com', '12345678900', '1234-5678'),
('Maria Oliveira', 'maria.oliveira@example.com', '98765432100', '8765-4321');