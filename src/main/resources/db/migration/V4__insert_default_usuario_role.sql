-- V4__insert_default_usuario_role.sql
-- Insert default usuario_role associations

INSERT INTO usuario_role (usuario_id, role_id)
SELECT u.id, r.id
FROM usuario u
CROSS JOIN role r
WHERE r.nome = 'LEITURA_ESCRITA'
AND NOT EXISTS (
    SELECT 1 FROM usuario_role ur
    WHERE ur.usuario_id = u.id AND ur.role_id = r.id
);

