CREATE TABLE `agendamentos` (
  `id` bigint(20) NOT NULL,
  `data_consulta` date DEFAULT NULL,
  `id_especialidade` bigint(20) DEFAULT NULL,
  `id_horario` bigint(20) DEFAULT NULL,
  `id_profissional` bigint(20) DEFAULT NULL,
  `id_paciente` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `especialidades`
--

CREATE TABLE `especialidades` (
  `id` bigint(20) NOT NULL,
  `descricao` text,
  `titulo` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `horas`
--

CREATE TABLE `horas` (
  `id` bigint(20) NOT NULL,
  `hora_minuto` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `pacientes`
--

CREATE TABLE `pacientes` (
  `id` bigint(20) NOT NULL,
  `data_nascimento` date NOT NULL,
  `nome` varchar(255) NOT NULL,
  `id_usuario` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `perfis`
--

CREATE TABLE `perfis` (
  `id` bigint(20) NOT NULL,
  `descricao` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `profissionais`
--

CREATE TABLE `profissionais` (
  `id` bigint(20) NOT NULL,
  `conselho` int(11) NOT NULL,
  `data_inscricao` date NOT NULL,
  `nome` varchar(255) NOT NULL,
  `id_usuario` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `profissionais_tem_especialidades`
--

CREATE TABLE `profissionais_tem_especialidades` (
  `id_especialidade` bigint(20) NOT NULL,
  `id_profissional` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `usuarios`
--

CREATE TABLE `usuarios` (
  `id` bigint(20) NOT NULL,
  `ativo` tinyint(1) NOT NULL,
  `email` varchar(255) NOT NULL,
  `senha` varchar(255) NOT NULL,
  `codigo_verificador` varchar(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `usuarios_tem_perfis`
--

CREATE TABLE `usuarios_tem_perfis` (
  `usuario_id` bigint(20) NOT NULL,
  `perfil_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `agendamentos`
--
ALTER TABLE `agendamentos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_ESPECIALIDADE_ID` (`id_especialidade`),
  ADD KEY `FK_HORA_ID` (`id_horario`),
  ADD KEY `FK_profissional_ID` (`id_profissional`),
  ADD KEY `FK_PACIENTE_ID` (`id_paciente`);

--
-- Indexes for table `especialidades`
--
ALTER TABLE `especialidades`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_TITULO` (`titulo`),
  ADD KEY `IDX_ESPECIALIDADE_TITULO` (`titulo`);

--
-- Indexes for table `horas`
--
ALTER TABLE `horas`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_HORA_MINUTO` (`hora_minuto`),
  ADD KEY `IDX_HORA_MINUTO` (`hora_minuto`);

--
-- Indexes for table `pacientes`
--
ALTER TABLE `pacientes`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_PACIENTE_NOME` (`nome`),
  ADD KEY `FK_PACIENTE_USUARIO_ID` (`id_usuario`);

--
-- Indexes for table `perfis`
--
ALTER TABLE `perfis`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_PERFIL_DESCRICAO` (`descricao`);

--
-- Indexes for table `profissionais`
--
ALTER TABLE `profissionais`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_conselho` (`conselho`),
  ADD UNIQUE KEY `UK_NOME` (`nome`),
  ADD UNIQUE KEY `UK_USUARIO_ID` (`id_usuario`);

--
-- Indexes for table `profissionais_tem_especialidades`
--
ALTER TABLE `profissionais_tem_especialidades`
  ADD UNIQUE KEY `profissional_UNIQUE_ESPECIALIZACAO` (`id_especialidade`,`id_profissional`),
  ADD KEY `FK_ESPECIALIDADE_profissional_ID` (`id_profissional`);

--
-- Indexes for table `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_USUARIO_EMAIL` (`email`),
  ADD KEY `IDX_USUARIO_EMAIL` (`email`);

--
-- Indexes for table `usuarios_tem_perfis`
--
ALTER TABLE `usuarios_tem_perfis`
  ADD KEY `FK_USUARIO_TEM_PERFIL_ID` (`perfil_id`),
  ADD KEY `FK_PERFIL_TEM_USUARIO_ID` (`usuario_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `agendamentos`
--
ALTER TABLE `agendamentos`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `especialidades`
--
ALTER TABLE `especialidades`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `horas`
--
ALTER TABLE `horas`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `pacientes`
--
ALTER TABLE `pacientes`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `perfis`
--
ALTER TABLE `perfis`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `profissionais`
--
ALTER TABLE `profissionais`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Limitadores para a tabela `agendamentos`
--
ALTER TABLE `agendamentos`
  ADD CONSTRAINT `FK_ESPECIALIDADE_ID` FOREIGN KEY (`id_especialidade`) REFERENCES `especialidades` (`id`),
  ADD CONSTRAINT `FK_HORA_ID` FOREIGN KEY (`id_horario`) REFERENCES `horas` (`id`),
  ADD CONSTRAINT `FK_PACIENTE_ID` FOREIGN KEY (`id_paciente`) REFERENCES `pacientes` (`id`),
  ADD CONSTRAINT `FK_profissional_ID` FOREIGN KEY (`id_profissional`) REFERENCES `profissionais` (`id`);

--
-- Limitadores para a tabela `pacientes`
--
ALTER TABLE `pacientes`
  ADD CONSTRAINT `FK_PACIENTE_USUARIO_ID` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`);

--
-- Limitadores para a tabela `profissionais`
--
ALTER TABLE `profissionais`
  ADD CONSTRAINT `FK_USUARIO_ID` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`);

--
-- Limitadores para a tabela `profissionais_tem_especialidades`
--
ALTER TABLE `profissionais_tem_especialidades`
  ADD CONSTRAINT `FK_ESPECIALIDADE_profissional_ID` FOREIGN KEY (`id_profissional`) REFERENCES `profissionais` (`id`),
  ADD CONSTRAINT `FK_profissional_ESPECIALIDADE_ID` FOREIGN KEY (`id_especialidade`) REFERENCES `especialidades` (`id`);

--
-- Limitadores para a tabela `usuarios_tem_perfis`
--
ALTER TABLE `usuarios_tem_perfis`
  ADD CONSTRAINT `FK_PERFIL_TEM_USUARIO_ID` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`),
  ADD CONSTRAINT `FK_USUARIO_TEM_PERFIL_ID` FOREIGN KEY (`perfil_id`) REFERENCES `perfis` (`id`);
COMMIT;
