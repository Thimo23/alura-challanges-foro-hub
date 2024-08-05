
ALTER TABLE topicos ADD COLUMN curso_id BIGINT;

ALTER TABLE topicos DROP COLUMN curso;

ALTER TABLE topicos
ADD CONSTRAINT fk_cursos_id
FOREIGN KEY (curso_id) REFERENCES cursos(id);