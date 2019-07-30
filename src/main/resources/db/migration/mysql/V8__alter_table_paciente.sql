ALTER TABLE `pacientes` ADD 
`sexo` CHAR(1) NOT NULL 
AFTER `data_nascimento`;

ALTER TABLE `pacientes` ADD 
`id_curso` BIGINT(20) NOT NULL
AFTER `data_nascimento`;

ALTER TABLE `pacientes` ALTER 
`id_curso` SET DEFAULT 1;

COMMIT;