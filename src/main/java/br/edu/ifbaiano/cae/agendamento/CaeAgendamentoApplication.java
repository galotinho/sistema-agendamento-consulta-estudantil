package br.edu.ifbaiano.cae.agendamento;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class CaeAgendamentoApplication{
//implements CommandLineRunner
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
	
}
