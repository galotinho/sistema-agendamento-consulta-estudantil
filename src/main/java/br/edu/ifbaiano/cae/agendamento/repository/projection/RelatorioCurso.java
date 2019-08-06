package br.edu.ifbaiano.cae.agendamento.repository.projection;

public class RelatorioCurso {

	private String curso;
	private int totalConsultasAgendadas;
	private int totalConsultasRealizadas;
	private Double percNaoComparecimento;
	
	public RelatorioCurso() {
		
	}
	
	public String getCurso() {
		return curso;
	}
	public void setCurso(String curso) {
		this.curso = curso;
	}
	
	public int getTotalConsultasAgendadas() {
		return totalConsultasAgendadas;
	}
	public void setTotalConsultasAgendadas(int totalConsultasAgendadas) {
		this.totalConsultasAgendadas = totalConsultasAgendadas;
	}
	public int getTotalConsultasRealizadas() {
		return totalConsultasRealizadas;
	}
	public void setTotalConsultasRealizadas(int totalConsultasRealizadas) {
		this.totalConsultasRealizadas = totalConsultasRealizadas;
	}
	public Double getPercNaoComparecimento() {
		return percNaoComparecimento;
	}
	public void setPercNaoComparecimento(Double percNaoComparecimento) {
		this.percNaoComparecimento = percNaoComparecimento;
	}
	
	
}

