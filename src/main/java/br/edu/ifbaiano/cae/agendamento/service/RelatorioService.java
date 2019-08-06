package br.edu.ifbaiano.cae.agendamento.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifbaiano.cae.agendamento.domain.Curso;
import br.edu.ifbaiano.cae.agendamento.domain.Especialidade;
import br.edu.ifbaiano.cae.agendamento.repository.AgendamentoRepository;
import br.edu.ifbaiano.cae.agendamento.repository.CursoRepository;
import br.edu.ifbaiano.cae.agendamento.repository.EspecialidadeRepository;
import br.edu.ifbaiano.cae.agendamento.repository.projection.Relatorio;
import br.edu.ifbaiano.cae.agendamento.repository.projection.RelatorioCurso;
import br.edu.ifbaiano.cae.agendamento.repository.projection.RelatorioEspecialidade;

@Service
public class RelatorioService {
	
	@Autowired
	private AgendamentoRepository agendamentoRepository;
	@Autowired
	private EspecialidadeRepository especialidadeRepository;
	@Autowired
	private CursoRepository cursoRepository;
	
	@Transactional(readOnly=true)
	public Relatorio listarTudoPorData(LocalDate dataInicial, LocalDate dataFinal) {
		
		Relatorio relatorio = new Relatorio();
		relatorio.setTotalConsultasAgendadas(agendamentoRepository.consultasAgendadasPorData(dataInicial, dataFinal));
		relatorio.setTotalConsultasRealizadas(agendamentoRepository.consultasRealizadasPorData(dataInicial, dataFinal));
		
		Double percentual = 100*(1-((double)relatorio.getTotalConsultasRealizadas()/(double)relatorio.getTotalConsultasAgendadas()));
		if(Double.isNaN(percentual)) {
			relatorio.setPercNaoComparecimento(0.0);
		}
		else{
			relatorio.setPercNaoComparecimento(percentual);
		}
		
		return relatorio;
	}

	@Transactional(readOnly=true)
	public List<RelatorioEspecialidade> listarTudoPorEspecialidadePorData(LocalDate dataInicial, LocalDate dataFinal) {
		
		List<RelatorioEspecialidade> lista = new ArrayList<RelatorioEspecialidade>();
		List<Especialidade> especialidades = especialidadeRepository.findAll();
		
		for(int i=0; i<especialidades.size(); i++) {
			RelatorioEspecialidade dado = new RelatorioEspecialidade();
			Especialidade esp = especialidades.get(i);
			
			dado.setEspecialidade(esp.getTitulo());
			dado.setTotalConsultasAgendadas(agendamentoRepository.consultasAgendadasPorEspecialidadeEData(esp.getTitulo(), dataInicial, dataFinal));
			dado.setTotalConsultasRealizadas(agendamentoRepository.consultasRealizadasPorEspecialidadeEData(esp.getTitulo(), dataInicial, dataFinal));
			
			Double percentual = 100*(1-((double)dado.getTotalConsultasRealizadas()/(double)dado.getTotalConsultasAgendadas()));
			if(Double.isNaN(percentual)) {
				dado.setPercNaoComparecimento(0.0);
			}
			else{
				dado.setPercNaoComparecimento(percentual);
			}
			
			lista.add(dado);
		}
		
		return lista;
	}

	@Transactional(readOnly=true)
	public List<RelatorioCurso> listarTudoPorCursoPorData(LocalDate dataInicial, LocalDate dataFinal) {
		
		List<RelatorioCurso> lista = new ArrayList<RelatorioCurso>();
		List<Curso> cursos = cursoRepository.findAll();
		
		for(int i=0; i<cursos.size(); i++) {
			RelatorioCurso dado = new RelatorioCurso();
			Curso cur = cursos.get(i);
			
			dado.setCurso(cur.getNome());
			dado.setTotalConsultasAgendadas(agendamentoRepository.consultasAgendadasPorCursoEData(cur.getNome(),dataInicial, dataFinal));
			dado.setTotalConsultasRealizadas(agendamentoRepository.consultasRealizadasPorCursoEData(cur.getNome(),dataInicial, dataFinal));
			
			Double percentual = 100*(1-((double)dado.getTotalConsultasRealizadas()/(double)dado.getTotalConsultasAgendadas()));
			if(Double.isNaN(percentual)) {
				dado.setPercNaoComparecimento(0.0);
			}
			else{
				dado.setPercNaoComparecimento(percentual);
			}
			
			lista.add(dado);
		}
		
		return lista;
	}

}
