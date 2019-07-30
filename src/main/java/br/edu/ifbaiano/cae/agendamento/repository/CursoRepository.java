package br.edu.ifbaiano.cae.agendamento.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.edu.ifbaiano.cae.agendamento.domain.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long>{

	@Query("select c from Curso c where c.nome like %:search%")
	Page<Curso> findAllByNome(String search, Pageable pageable);

}
