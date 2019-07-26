package br.edu.ifbaiano.cae.agendamento.domain;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;


@SuppressWarnings("serial")
@Entity
@Table(name = "data_especialista", indexes = {@Index(name = "idx_data", columnList = "data_consulta")})

public class Data extends AbstractEntity {

	@Column(name="data_consulta", nullable = false)
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dataDisponivel;
	
	@ManyToOne
	@JoinColumn(name="id_profissional")
	private Profissional profissional;
	
	@ManyToMany
	@JoinTable(
			name = "data_tem_horarios",
			joinColumns = @JoinColumn(name = "data_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "horario_id", referencedColumnName = "id")
    )
	private List<Horario> horarios;
	
	@Transient
	private String[] horarioLista;
	
	public LocalDate getDataDisponivel() {
		return dataDisponivel;
	}

	public void setDataDisponivel(LocalDate dataDisponivel) {
		this.dataDisponivel = dataDisponivel;
	}

	public Profissional getProfissional() {
		return profissional;
	}

	public void setProfissional(Profissional profissional) {
		this.profissional = profissional;
	}

	public List<Horario> getHorarios() {
		return horarios;
	}

	public void setHorarios(List<Horario> horarios) {
		this.horarios = horarios;
	}

	@Transient
	public String[] getHorarioLista() {
		return horarioLista;
	}

	@Transient
	public void setHorarioLista(String[] horarioLista) {
		this.horarioLista = horarioLista;
	}
	
}
