package br.edu.ifbaiano.cae.agendamento.repository.projection;

import br.edu.ifbaiano.cae.agendamento.domain.Especialidade;
import br.edu.ifbaiano.cae.agendamento.domain.Paciente;
import br.edu.ifbaiano.cae.agendamento.domain.Profissional;

public interface HistoricoPaciente {

	Long getId();
	
	Paciente getPaciente();
	
	String getDataConsulta();
	
	Profissional getProfissional();
	
	Especialidade getEspecialidade();
}
