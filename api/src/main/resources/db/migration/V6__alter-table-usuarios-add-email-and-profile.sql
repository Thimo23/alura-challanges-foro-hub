ALTER TABLE usuarios ADD COLUMN email VARCHAR(255);
ALTER TABLE usuarios ADD COLUMN perfil_id BIGINT;

ALTER TABLE usuarios
ADD CONSTRAINT fk_perfil_id
FOREIGN KEY (perfil_id) REFERENCES perfiles(id);

ALTER TABLE usuarios
ADD CONSTRAINT unique_perfil_id
UNIQUE (perfil_id);