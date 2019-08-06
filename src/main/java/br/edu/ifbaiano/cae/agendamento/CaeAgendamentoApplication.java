package br.edu.ifbaiano.cae.agendamento;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CaeAgendamentoApplication {
//implements CommandLineRunner{
	//@Autowired
	//private ProfissionalRepository repository;
	public static void main(String[] args) {
		SpringApplication.run(CaeAgendamentoApplication.class, args);
		
	}
/*
	@Autowired
	EmailService service;

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		service.enviarPedidoDeConfirmacaoDeCadastro("galotinhodelta@gmail.com", "3253fsd");
	}
	*/
	/*
	@Autowired
	JavaMailSender sender;
	
	@Override
	public void run(String... args) throws Exception {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo("galotinhodelta@gmail.com");
		mail.setText("Teste texto");
		mail.setSubject("Teste Titulo");
		
		sender.send(mail);
		
	}
	*/
	/*
	@Override
	public void run(String... args) throws Exception {
		List<Horario> horarios = new ArrayList<Horario>();
		horarios = repository.findHorariosDisponibilizados("dentista@ifbaiano.edu.br", LocalDate.parse("2019-07-22"));
		
		for(int i=0; i<horarios.size(); i++) {
			System.out.println(horarios.get(i).getHoraMinuto());
		}
		
	}
	*/
/*
	@Override
	public void run(String... args) throws Exception {
		
		List<Horario> horariosDisponiveis = repository.findHorariosDisponibilizadosPorProfissional(1L, LocalDate.parse("2019-07-03"));
		List<Horario> horariosMarcados = repository.findByProfissionalIdAndDataNotHorarioAgendado(1L, LocalDate.parse("2019-07-03"));
		horariosDisponiveis.removeAll(horariosMarcados);
		
		for(int i=0; i<horariosDisponiveis.size(); i++) {
			System.out.println(horariosDisponiveis.get(i).getHoraMinuto());
		}		
		
	}
	*/
	
}
