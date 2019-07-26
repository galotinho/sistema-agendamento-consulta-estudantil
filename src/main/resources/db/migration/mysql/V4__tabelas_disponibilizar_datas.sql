CREATE TABLE `data_especialista` ( 
`id` BIGINT(20) NOT NULL , 
`data_consulta` DATE NOT NULL , 
`id_profissional` BIGINT(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `data_tem_horarios` (
  `data_id` BIGINT(20) NOT NULL,
  `horario_id` BIGINT(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


ALTER TABLE `data_especialista`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `data_especialista`
  MODIFY `id` BIGINT(20) NOT NULL AUTO_INCREMENT;

ALTER TABLE `data_tem_horarios`
  ADD KEY `FK_DATA_TEM_HORARIO_ID` (`data_id`),
  ADD KEY `FK_HORARIO_TEM_DATA_ID` (`horario_id`);

ALTER TABLE `data_tem_horarios`
  ADD CONSTRAINT `FK_DATA_TEM_HORARIO_ID` FOREIGN KEY (`data_id`) REFERENCES `data_especialista` (`id`),
  ADD CONSTRAINT `FK_HORARIO_TEM_DATA_ID` FOREIGN KEY (`horario_id`) REFERENCES `horas` (`id`);
COMMIT;  
