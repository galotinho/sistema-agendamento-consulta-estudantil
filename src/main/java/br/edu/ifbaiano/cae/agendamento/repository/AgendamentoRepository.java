package br.edu.ifbaiano.cae.agendamento.repository;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.edu.ifbaiano.cae.agendamento.domain.Agendamento;
import br.edu.ifbaiano.cae.agendamento.repository.projection.HistoricoPaciente;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long>{

	@Query("select a.id as id,"
				+ "a.paciente as paciente,"
				+ "CONCAT(a.dataConsulta, ' ', a.horario.horaMinuto) as dataConsulta,"
				+ "a.profissional as profissional,"
				+ "a.especialidade as especialidade "
			+ "from Agendamento a "
			+ "where a.paciente.usuario.email like :email")
	Page<HistoricoPaciente> findHistoricoByPacienteEmail(String email, Pageable pageable);

	@Query("select a.id as id,"
			+ "a.paciente as paciente,"
			+ "CONCAT(a.dataConsulta, ' ', a.horario.horaMinuto) as dataConsulta,"
			+ "a.profissional as profissional,"
			+ "a.especialidade as especialidade "
		+ "from Agendamento a "
		+ "where a.profissional.usuario.email like :email")	
	
	Page<HistoricoPaciente> findHistoricoByProfissionalEmail(String email, Pageable pageable);

	@Query("select a from Agendamento a "
			+ "where "
			+ "	(a.id = :id AND a.paciente.usuario.email like :email) "
			+ " OR "
			+ " (a.id = :id AND a.profissional.usuario.email like :email)")
	Optional<Agendamento> findByIdAndPacienteOrProfissionalEmail(Long id, String email);

	@Query("select COUNT(a.id) from Agendamento a "
			+ "where a.especialidade.titulo like :titulo")
	int consultasAgendadasPorEspecialidade(String titulo);

	@Query("select COUNT(a.id) from Agendamento a "
			+ "where a.especialidade.titulo like :titulo "
			+ "AND a.comparecimento = 1")
	int consultasRealizadasPorEspecialidade(String titulo);

	@Query("select COUNT(a.id) from Agendamento a "
			+ "where a.paciente.curso.nome like :nome")
	int consultasAgendadasPorCurso(String nome);
	
	@Query("select COUNT(a.id) from Agendamento a "
			+ "where a.paciente.curso.nome like :nome "
			+ "AND a.comparecimento = 1")
	int consultasRealizadasPorCurso(String nome);

	@Query("select COUNT(a.id) from Agendamento a "
			+ "where a.dataConsulta >= :dataInicial "
			+ "AND a.dataConsulta <= :dataFinal")
	int consultasAgendadasPorData(LocalDate dataInicial, LocalDate dataFinal);

	@Query("select COUNT(a.id) from Agendamento a "
			+ "where a.dataConsulta >= :dataInicial "
			+ "AND a.dataConsulta <= :dataFinal AND "
			+ "a.comparecimento = 1")
	int consultasRealizadasPorData(LocalDate dataInicial, LocalDate dataFinal);

	@Query("select COUNT(a.id) from Agendamento a "
			+ "where a.dataConsulta >= :dataInicial "
			+ "AND a.dataConsulta <= :dataFinal AND "
			+ "a.especialidade.titulo like :titulo")
	int consultasAgendadasPorEspecialidadeEData(String titulo, LocalDate dataInicial, LocalDate dataFinal);

	@Query("select COUNT(a.id) from Agendamento a "
			+ "where a.dataConsulta >= :dataInicial "
			+ "AND a.dataConsulta <= :dataFinal AND "
			+ "a.comparecimento = 1 AND a.especialidade.titulo like :titulo")
	int consultasRealizadasPorEspecialidadeEData(String titulo, LocalDate dataInicial, LocalDate dataFinal);

	@Query("select COUNT(a.id) from Agendamento a "
			+ "where a.dataConsulta >= :dataInicial "
			+ "AND a.dataConsulta <= :dataFinal AND "
			+ "a.paciente.curso.nome like :nome")
	int consultasAgendadasPorCursoEData(String nome, LocalDate dataInicial, LocalDate dataFinal);

	@Query("select COUNT(a.id) from Agendamento a "
			+ "where a.dataConsulta >= :dataInicial "
			+ "AND a.dataConsulta <= :dataFinal AND "
			+ "a.comparecimento = 1 AND a.paciente.curso.nome like :nome")
	int consultasRealizadasPorCursoEData(String nome, LocalDate dataInicial, LocalDate dataFinal);

}
