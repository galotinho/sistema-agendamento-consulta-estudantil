package br.edu.ifbaiano.cae.agendamento.domain;

public enum PerfilTipo {
	ADMIN(1, "ADMIN"), PROFISSIONAL(2, "PROFISSIONAL"), PACIENTE(3, "PACIENTE");
	
	private long cod;
	private String desc;

	private PerfilTipo(long cod, String desc) {
		this.cod = cod;
		this.desc = desc;
	}

	public long getCod() {
		return cod;
	}

	public String getDesc() {
		return desc;
	}
}
