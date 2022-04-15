package br.edu.ifbaiano.cae.agendamento.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private SpringTemplateEngine template;
	
	@Value("${endereco-web}")
	private String endereco;
	
	public void enviarPedidoDeConfirmacaoDeCadastro(String destino, String codigo) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = 
				new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");
		
		Context context = new Context();
		context.setVariable("titulo", "Bem vindo à Coordenação de Assistência ao Estudante");
		context.setVariable("texto", "Precisamos que confirme seu cadastro, clicando no link abaixo");
		context.setVariable("linkConfirmacao", 
				endereco+"/u/confirmacao/cadastro?codigo=" + codigo);
		
		String html = template.process("mail/confirmacao", context);
		helper.setTo(destino);
		helper.setText(html, true);
		helper.setSubject("Confirmacao de Cadastro");
		helper.setFrom("cae.ifbaiano.urucuca@gmail.com");
		
		helper.addInline("logo", new ClassPathResource("/static/image/marca-horizontal.png"));
		
		mailSender.send(message);
	}
	
	public void enviarPedidoRedefinicaoSenha(String destino, String verificador) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = 
        		new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");
        
        Context context = new Context();
        context.setVariable("titulo", "Redefinição de Senha");
        context.setVariable("texto", "Para redefinir sua senha use o código de verficação " +
                "quando exigido no formulário." );
        context.setVariable("verificador", verificador);
        
        String html = template.process("mail/confirmacao", context);        
        helper.setTo(destino);
        helper.setText(html, true);
        helper.setSubject("Redefinição de Senha");
        helper.setFrom("cae.ifbaiano.urucuca@gmail.com");

        helper.addInline("logo", new ClassPathResource("/static/image/marca-horizontal.png"));  
       
        mailSender.send(message);		
	}
	
	
	public void enviarEmailCancelamentoConsulta(String destinoPaciente, String destinoProfissional, String data, String horario, String especialidade) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = 
				new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");
		
		Context context = new Context();
		context.setVariable("titulo", "Bem vindo à Coordenação de Assistência ao Estudante");
		context.setVariable("texto", "Informamos que a consulta abaixo foi cancelada!");
		context.setVariable("data", data);
		context.setVariable("horario", horario);
		context.setVariable("especialidade", especialidade);
		String html = template.process("mail/cancelamento", context);
		String destinatarios[] = {destinoPaciente, destinoProfissional};
		helper.setTo(destinatarios);
		helper.setText(html, true);
		helper.setSubject("Cancelamento de Consulta");
		helper.setFrom("cae.ifbaiano.urucuca@gmail.com");
		
		helper.addInline("logo", new ClassPathResource("/static/image/marca-horizontal.png"));
		
		mailSender.send(message);
	}
}
