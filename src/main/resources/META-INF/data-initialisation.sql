INSERT INTO `organisation`.`country` (name, code) VALUES ("Colombia","COL");

INSERT INTO `organisation`.`state` (name, code, country_id) VALUES ("Cundinamarca","CUND", (SELECT id FROM `organisation`.`country` WHERE code = "COL" ));
INSERT INTO `organisation`.`state` (name, code, country_id) VALUES ("Antioquia","ANTQ", (SELECT id FROM `organisation`.`country` WHERE code = "COL" ));

INSERT INTO `organisation`.`city` (name, code, state_id) VALUES ("Medellín","MDE", (SELECT id FROM `organisation`.`state` WHERE code = "ANTQ" ));
INSERT INTO `organisation`.`city` (name, code, state_id) VALUES ("Santa fé de Bogotá","BOG", (SELECT id FROM `organisation`.`state` WHERE code = "CUND" ));

INSERT INTO `organisation`.`generic_area` (name, description) VALUES ("Administración","Encargados de el area contable y de recursos.");
INSERT INTO `organisation`.`generic_area` (name, description) VALUES ("Dirección","Encargados de definir los lineamientos de desarrollo.");
INSERT INTO `organisation`.`generic_area` (name, description) VALUES ("Ventas","Area engargada de publicidad y mercadeo.");
INSERT INTO `organisation`.`generic_area` (name, description) VALUES ("Recursos Humanos","Area engargada de personal.");
INSERT INTO `organisation`.`generic_area` (name, description) VALUES ("IT","Area engargada de tecnologías de información.");
INSERT INTO `organisation`.`generic_area` (name, description) VALUES ("Operación","Encargados del mantenimiento las instalaciones.");