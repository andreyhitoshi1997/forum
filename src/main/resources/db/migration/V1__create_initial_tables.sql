-- V1__create_initial_tables.sql
-- Migração inicial do banco de dados do fórum
-- Criado em: 2025-12-02

-- ======================================
-- Tabela: usuario
-- ======================================
CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ======================================
-- Tabela: curso
-- ======================================
CREATE TABLE curso (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    categoria VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ======================================
-- Tabela: topico
-- ======================================
CREATE TABLE topico (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    mensagem TEXT NOT NULL,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) NOT NULL DEFAULT 'NAO_RESPONDIDO',
    curso_id BIGINT NOT NULL,
    autor_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_topico_curso FOREIGN KEY (curso_id) REFERENCES curso(id) ON DELETE CASCADE,
    CONSTRAINT fk_topico_autor FOREIGN KEY (autor_id) REFERENCES usuario(id) ON DELETE CASCADE
);

-- ======================================
-- Tabela: resposta
-- ======================================
CREATE TABLE resposta (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    mensagem TEXT NOT NULL,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    topico_id BIGINT NOT NULL,
    autor_id BIGINT NOT NULL,
    solucao BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_resposta_topico FOREIGN KEY (topico_id) REFERENCES topico(id) ON DELETE CASCADE,
    CONSTRAINT fk_resposta_autor FOREIGN KEY (autor_id) REFERENCES usuario(id) ON DELETE CASCADE
);

-- ======================================
-- Índices para otimização de consultas
-- ======================================

-- Índices para tabela topico
CREATE INDEX idx_topico_curso_id ON topico(curso_id);
CREATE INDEX idx_topico_autor_id ON topico(autor_id);
CREATE INDEX idx_topico_status ON topico(status);
CREATE INDEX idx_topico_data_criacao ON topico(data_criacao DESC);

-- Índices para tabela resposta
CREATE INDEX idx_resposta_topico_id ON resposta(topico_id);
CREATE INDEX idx_resposta_autor_id ON resposta(autor_id);
CREATE INDEX idx_resposta_solucao ON resposta(solucao);
CREATE INDEX idx_resposta_data_criacao ON resposta(data_criacao DESC);

-- Índices para tabela usuario
CREATE INDEX idx_usuario_email ON usuario(email);

-- Índices para tabela curso
CREATE INDEX idx_curso_categoria ON curso(categoria);

