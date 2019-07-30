package br.edu.ifbaiano.cae.agendamento.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifbaiano.cae.agendamento.repository.AgendamentoRepository;
import br.edu.ifbaiano.cae.agendamento.repository.projection.Relatorio;

@Service
public class RelatorioService {
	
	@Autowired
	private AgendamentoRepository agendamentoRepository;

	@Transactional(readOnly=true)
	public Relatorio listarTudo() {
		Relatorio relatorio = new Relatorio();
		relatorio.setTotalConsultasAgendadas(agendamentoRepository.consultasAgendadas());
		relatorio.setTotalConsultasRealizadas(agendamentoRepository.consultasRealizadas());
		relatorio.setPercNaoComparecimento(100*(1-((double)relatorio.getTotalConsultasRealizadas()/(double)relatorio.getTotalConsultasAgendadas())));
		
		return relatorio;
	}

}
