package br.edu.ifbaiano.cae.agendamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifbaiano.cae.agendamento.domain.Data;

public interface DataRepository extends JpaRepository<Data, Long> {

}
