CREATE TABLE respuestas (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
mensaje TEXT NOT NULL,
fecha_creacion DATE NOT NULL,
autor_id BIGINT NOT NULL,
topico_id BIGINT NOT NULL,
solucion TINYINT(1) DEFAULT 0,
FOREIGN KEY (topico_id) REFERENCES topicos(id) ON DELETE CASCADE,
FOREIGN KEY (autor_id) REFERENCES usuarios(id)
);

ALTER TABLE respuestas
ADD CONSTRAINT unique_solucion_per_topico
UNIQUE (topico_id, solucion);