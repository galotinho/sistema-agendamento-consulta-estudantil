package br.edu.ifbaiano.cae.agendamento.domain;

import java.util.List;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "especialidades", indexes = {@Index(name = "idx_especialidade_titulo", columnList = "titulo")})
public class Especialidade extends AbstractEntity {
	
	@Column(name = "titulo", unique = true, nullable = false)
	private String titulo;
	
	@Column(name = "descricao", columnDefinition = "TEXT")
	private String descricao;
	
	@ManyToMany
	@JoinTable(
			name = "profissionais_tem_especialidades",
			joinColumns = @JoinColumn(name = "id_especialidade", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "id_profissional", referencedColumnName = "id")
    )
	private List<Profissional> profissionais;	

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}	

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Profissional> getProfissionais() {
		return profissionais;
	}

	public void setProfissional(List<Profissional> profissionais) {
		this.profissionais = profissionais;
	}
}
