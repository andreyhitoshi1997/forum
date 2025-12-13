-- V3__add_role_and_usuario_role.sql
-- Add role and usuario_role tables

-- ======================================
-- Tabela: role
-- ======================================
CREATE TABLE role(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE
);

-- ======================================
-- Tabela: usuario_role
-- ======================================
CREATE TABLE usuario_role(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE
);

-- Insert default roles
INSERT INTO role(nome) VALUES ('LEITURA_ESCRITA');
INSERT INTO role(nome) VALUES ('ADMINISTRADOR');

