package br.edu.ifbaiano.cae.agendamento.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.edu.ifbaiano.cae.agendamento.domain.Agendamento;
import br.edu.ifbaiano.cae.agendamento.domain.Horario;
import br.edu.ifbaiano.cae.agendamento.repository.projection.HistoricoPaciente;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long>{

	@Query("select h "
			+ "from Horario h "
			+ "where not exists("
				+ "select a.horario.id "
					+ "from Agendamento a "
					+ "where "
						+ "a.profissional.id = :id and "
						+ "a.dataConsulta = :data and "
						+ "a.horario.id = h.id "
			+ ") "
			+ "order by h.horaMinuto asc")
	List<Horario> findByProfissionalIdAndDataNotHorarioAgendado(Long id, LocalDate data);

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

}
