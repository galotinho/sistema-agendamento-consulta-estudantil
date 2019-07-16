INSERT INTO `usuarios`(`id`, `ativo`, `email`, `senha`, `codigo_verificador`) VALUES (1,1,'rafael.lopes@ifbaiano.edu.br','$2a$10$VscU.y/g5jdwDtNSDEKYLeAqus2RcDLbQpfxNDE9mrBkcrx6wC1li',null);
INSERT INTO `perfis` (`id`, `descricao`) VALUES ('1', 'ADMIN'), ('2', 'PROFISSIONAL'), ('3', 'PACIENTE');
INSERT INTO `usuarios_tem_perfis` (`usuario_id`, `perfil_id`) VALUES ('1', '1');