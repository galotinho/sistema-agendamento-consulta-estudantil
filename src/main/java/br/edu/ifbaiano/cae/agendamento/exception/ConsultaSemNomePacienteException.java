package br.edu.ifbaiano.cae.agendamento.exception;

@SuppressWarnings("serial")
public class ConsultaSemNomePacienteException extends RuntimeException {

		public ConsultaSemNomePacienteException(String message) {
			super(message);
		}
}
