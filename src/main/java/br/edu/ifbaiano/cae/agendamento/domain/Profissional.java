package br.edu.ifbaiano.cae.agendamento.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("serial")
@Entity
@Table(name = "profissionais")
public class Profissional extends AbstractEntity {

	@Column(name = "nome", unique = true, nullable = false)
	private String nome;
	
	@Column(name = "conselho", unique = true, nullable = false)
	private Integer conselho;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "data_inscricao", nullable = false)
	private LocalDate dtInscricao;
	
	// evita recursividade quando o json de resposta for criado para a datatables.
	@JsonIgnore
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
			name = "profissionais_tem_especialidades",
			joinColumns = @JoinColumn(name = "id_profissional", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "id_especialidade", referencedColumnName = "id")
    )
	private Set<Especialidade> especialidades;
	
	// evita recursividade quando o json de resposta for criado para a datatables.
	@JsonIgnore
	@OneToMany(mappedBy = "profissional")
	private List<Agendamento> agendamentos;
	
	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	public Profissional() {
		super();
	}

	public Profissional(Long id) {
		super.setId(id);
	}

	public Profissional(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getConselho() {
		return conselho;
	}

	public void setConselho(Integer conselho) {
		this.conselho = conselho;
	}

	public LocalDate getDtInscricao() {
		return dtInscricao;
	}

	public void setDtInscricao(LocalDate dtInscricao) {
		this.dtInscricao = dtInscricao;
	}

	public Set<Especialidade> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(Set<Especialidade> especialidades) {
		this.especialidades = especialidades;
	}

	public List<Agendamento> getAgendamentos() {
		return agendamentos;
	}

	public void setAgendamentos(List<Agendamento> agendamentos) {
		this.agendamentos = agendamentos;
	}	

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
