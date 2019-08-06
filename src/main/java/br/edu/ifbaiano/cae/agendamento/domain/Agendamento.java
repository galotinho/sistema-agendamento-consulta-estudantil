package br.edu.ifbaiano.cae.agendamento.domain;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import javax.persistence.*;
import java.time.LocalDate;

@SuppressWarnings("serial")
@Entity
@Table(name = "agendamentos") 
public class Agendamento extends AbstractEntity {
	
	@ManyToOne
	@JoinColumn(name="id_especialidade")
	private Especialidade especialidade;
	
	@ManyToOne
	@JoinColumn(name="id_profissional")
	private Profissional profissional;
	
	@ManyToOne
	@JoinColumn(name="id_paciente")
	private Paciente paciente;
	
	@ManyToOne
	@JoinColumn(name="id_horario")
	private Horario horario; 

	@Column(name="data_consulta")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dataConsulta;
	
	@Column(name="comparecimento", nullable = false, columnDefinition = "TINYINT(1)")
	private boolean comparecimento;
	
	@Transient
	private String horarioS;
	
	@Transient
	private String dataS;
	
	public Especialidade getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}

	public Profissional getProfissional() {
		return profissional;
	}

	public void setProfissional(Profissional profissional) {
		this.profissional = profissional;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public LocalDate getDataConsulta() {
		return dataConsulta;
	}

	public void setDataConsulta(LocalDate dataConsulta) {
		this.dataConsulta = dataConsulta;
	}

	public Horario getHorario() {
		return horario;
	}

	public void setHorario(Horario horario) {
		this.horario = horario;
	}

	@Transient
	public String getHorarioS() {
		return horarioS;
	}

	@Transient
	public void setHorarioS(String horarioS) {
		this.horarioS = horarioS;
	}

	public boolean isComparecimento() {
		return comparecimento;
	}

	public void setComparecimento(boolean comparecimento) {
		this.comparecimento = comparecimento;
	}

	public String getDataS() {
		return dataS;
	}

	public void setDataS(String dataS) {
		this.dataS = dataS;
	}
}
