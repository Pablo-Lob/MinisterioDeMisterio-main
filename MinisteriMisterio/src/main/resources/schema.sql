DROP TABLE IF EXISTS artefacto_magico;

CREATE TABLE artefacto_magico  (
                                   id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                   nombre VARCHAR(100),
                                   nivel_peligrosidad VARCHAR(20),
                                   estado_procesamiento VARCHAR(50)
);