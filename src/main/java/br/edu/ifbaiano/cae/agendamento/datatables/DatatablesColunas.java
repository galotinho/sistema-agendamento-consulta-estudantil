package br.edu.ifbaiano.cae.agendamento.datatables;

public class DatatablesColunas {

	public static final String[] ESPECIALIDADES = {"id", "titulo"};

	public static final String[] PROFISSIONAIS = {"id", "nome", "conselho", "dtInscricao", "especialidades"};
	
	public static final String[] AGENDAMENTOS = {"id", "paciente.nome", "dataConsulta", "profissional.nome", "especialidade.titulo"};

	public static final String[] USUARIOS = {"id", "email", "ativo", "perfis"};	
	
	public static final String[] HORARIOS = {"id", "dataDisponivel", "horarios"};
	
	public static final String[] CURSOS = {"id", "nome"};
}
