package br.edu.ifbaiano.cae.agendamento.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.edu.ifbaiano.cae.agendamento.domain.Especialidade;

public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long>{

	@Query("select e from Especialidade e where e.titulo like :search%")
	Page<Especialidade> findAllByTitulo(String search, Pageable pageable);
	
	@Query("select e.titulo from Especialidade e where e.titulo like :termo%")
	List<String> findByTermo(String termo);

	@Query("select e from Especialidade e where e.titulo IN :titulos")
	Set<Especialidade> findByTituloIn(String[] titulos);
	
	@Query("select e from Especialidade e "
			+ "join e.profissionais p "
			+ "where p.id = :id")
	Page<Especialidade> findByIdProfissional(Long id, Pageable pageable);

}
