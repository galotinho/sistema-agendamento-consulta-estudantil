package br.edu.ifbaiano.cae.agendamento.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

}
