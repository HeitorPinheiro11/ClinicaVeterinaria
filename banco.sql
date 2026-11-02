CREATE DATABASE IF NOT EXISTS clinica_veterinaria;
USE clinica_veterinaria;

CREATE TABLE IF NOT EXISTS consultas (
    horario_index INT PRIMARY KEY,
    horario VARCHAR(10) NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    nome_animal VARCHAR(100) NOT NULL,
    idade INT NOT NULL,
    nome_dono VARCHAR(100) NOT NULL,
    telefone_dono VARCHAR(30) NOT NULL,
    caracteristica VARCHAR(50) NOT NULL,
    sintomas VARCHAR(500) NOT NULL
);

-- Dados de demonstração: os índices 0 e 1 correspondem aos horários 1 e 2 na tela.
INSERT IGNORE INTO consultas
    (horario_index, horario, tipo, nome_animal, idade, nome_dono, telefone_dono, caracteristica, sintomas)
VALUES
    (0, '09:00', 'Cachorro', 'Rex', 4, 'Ana Souza', '11999990000', 'Grande', 'Coceira na pata'),
    (1, '09:30', 'Gato', 'Mimi', 2, 'Bruno Lima', '11988887777', 'Dócil', 'Consulta de rotina');
