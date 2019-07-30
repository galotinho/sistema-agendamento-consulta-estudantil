CREATE TABLE `cursos` (
  `id` bigint(20) NOT NULL,
  `descricao` text,
  `nome` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for table `cursos`
--
ALTER TABLE `cursos`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_CURSO` (`nome`),
  ADD KEY `IDX_CURSO_NOME` (`nome`);

--
-- AUTO_INCREMENT for table `cursos`
--
ALTER TABLE `cursos`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
COMMIT;