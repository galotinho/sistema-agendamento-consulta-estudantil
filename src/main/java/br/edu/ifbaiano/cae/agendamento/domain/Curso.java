package br.edu.ifbaiano.cae.agendamento.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "cursos", indexes = {@Index(name = "idx_curso_nome", columnList = "nome")})
public class Curso extends AbstractEntity {
	
	@Column(name = "nome", unique = true, nullable = false)
	private String nome;
	
	@Column(name = "descricao", columnDefinition = "TEXT")
	private String descricao;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	
}
