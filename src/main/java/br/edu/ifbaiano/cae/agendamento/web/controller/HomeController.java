package br.edu.ifbaiano.cae.agendamento.web.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	// abrir pagina home
	@GetMapping({"/","/home"})
	public String home() {
		return "home";
	}
	
	@GetMapping("/login")  
	public String login() {   
	   return "login";  
	}
	
	@GetMapping("/login-error")  
	public String loginError(ModelMap model) {  
	    model.addAttribute("alerta", "erro");  
	    model.addAttribute("titulo", "Credenciais Inválidas!"); 
	    model.addAttribute("texto", "Login ou Senha Inválidos. Tente Novamente."); 
	    model.addAttribute("subtexto", "Acesso permitido apenas para cadastros já ativados!"); 
	    return "login";  
	}
	
	@GetMapping("/acesso-negado")  
	public String acessoNegado(ModelMap model, HttpServletResponse resp) {  
	    model.addAttribute("status", resp.getStatus());  
	    model.addAttribute("error", "Área Restrita"); 
	    model.addAttribute("message", "Você não tem permissão para acessar esta área ou realizar esta ação!"); 
	    return "error";  
	}
}
