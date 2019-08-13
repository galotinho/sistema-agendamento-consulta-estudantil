package br.edu.ifbaiano.cae.agendamento.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.edu.ifbaiano.cae.agendamento.domain.Horario;
import br.edu.ifbaiano.cae.agendamento.domain.Profissional;;

public interface ProfissionalRepository extends JpaRepository<Profissional, Long>{

	@Query("select p from Profissional p where p.usuario.id = :id ")
	Optional<Profissional> findByUsuarioId(Long id);

	@Query("select p from Profissional p where p.usuario.email like :email ")
	Optional<Profissional> findByEmail(String email);

	@Query("select distinct p from Profissional p "
			+ "join p.especialidades e "
			+ "where e.titulo like :titulo "
			+ "and p.usuario.ativo = true")
	List<Profissional> findByProfissionaisPorEspecialidade(String titulo);

	@Query("select p.id "
			+ "from Profissional p "
			+ "join p.especialidades e "
			+ "join p.agendamentos a "
			+ "where "
			+ "a.especialidade.id = :idEsp AND a.profissional.id = :idProf")
	Optional<Long> hasEspecialidadeAgendada(Long idProf, Long idEsp);

	@Query("select h "
			+ "from Horario h "
			+ "order by h.horaMinuto asc")
	List<Horario> findHorarios();

	@Query("select h "
			+ "from Horario h "
			+ "where h.id = :id")
	Horario findByIdHorario(Long id);

	@Query("select d.horarios "
			+ "from Data d "
			+ "where d.dataDisponivel = :data AND "
			+ "d.profissional.usuario.email like :email")
	List<Horario> findHorariosDisponibilizados(String email, LocalDate data);

	@Query("select d.horarios "
			+ "from Data d "
			+ "where d.dataDisponivel = :data AND "
			+ "d.profissional.id = :idProfissional")
	List<Horario> findHorariosDisponibilizadosPorProfissional(Long idProfissional, LocalDate data);

	@Query("select h "
			+ "from Horario h "
			+ "where exists("
				+ "select a.horario.id "
					+ "from Agendamento a "
					+ "where "
						+ "a.profissional.id = :id and "
						+ "a.dataConsulta = :data and "
						+ "a.horario.id = h.id "
			+ ") "
			+ "order by h.horaMinuto asc")
	List<Horario> findByProfissionalIdAndDataNotHorarioAgendado(Long id, LocalDate data);

	@Query("select d.dataDisponivel "
			+ "from Data d "
			+ "where d.profissional.id = :idProfissional AND "
			+ "d.dataDisponivel >= :dataAtual")
	List<LocalDate> findAllDatesAvailables(Long idProfissional, LocalDate dataAtual);

	@Query("select d.dataDisponivel "
			+ "from Data d "
			+ "where d.dataDisponivel = :data")
	List<LocalDate> findByData(LocalDate data);

	@Query("select d.dataDisponivel "
			+ "from Data d "
			+ "where d.dataDisponivel = :data AND "
			+ "d.id != :id")
	List<LocalDate> findByDataAndID(LocalDate data, Long id);

	
}
