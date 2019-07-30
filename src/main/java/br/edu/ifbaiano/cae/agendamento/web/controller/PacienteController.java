package br.edu.ifbaiano.cae.agendamento.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifbaiano.cae.agendamento.domain.Curso;
import br.edu.ifbaiano.cae.agendamento.domain.Paciente;
import br.edu.ifbaiano.cae.agendamento.domain.Usuario;
import br.edu.ifbaiano.cae.agendamento.service.PacienteService;
import br.edu.ifbaiano.cae.agendamento.service.UsuarioService;

@Controller
@RequestMapping("pacientes")
public class PacienteController {
	
	@Autowired
	private PacienteService service;
	@Autowired
	private UsuarioService usuarioService;
	
	// abrir pagina de dados pessoais do paciente
	@GetMapping("/dados")
	public String cadastrar(Paciente paciente, ModelMap model, @AuthenticationPrincipal User user) {
		paciente = service.buscarPorUsuarioEmail(user.getUsername());
		if (paciente.hasNotId()) {
			paciente.setUsuario(new Usuario(user.getUsername()));
			
		}
		model.addAttribute("paciente", paciente);
		return "paciente/cadastro";
	}
	
	// salvar o form de dados pessoais do paciente com verificacao de senha
	@PostMapping("/salvar")
	public String salvar(Paciente paciente, ModelMap model, @AuthenticationPrincipal User user) {
		Usuario u = usuarioService.buscarPorEmail(user.getUsername());
		if (UsuarioService.isSenhaCorreta(paciente.getUsuario().getSenha(), u.getSenha())) {
			paciente.setUsuario(u);
			service.salvar(paciente);
			model.addAttribute("sucesso", "Seus dados foram inseridos com sucesso.");
		} else {
			model.addAttribute("falha", "Sua senha não confere, tente novamente.");
		}
		return "paciente/cadastro";
	}	
	
	// editar o form de dados pessoais do paciente com verificacao de senha
	@PostMapping("/editar")
	public String editar(Paciente paciente, ModelMap model, @AuthenticationPrincipal User user) {
		Usuario u = usuarioService.buscarPorEmail(user.getUsername());
		if (UsuarioService.isSenhaCorreta(paciente.getUsuario().getSenha(), u.getSenha())) {
			service.editar(paciente);
			model.addAttribute("sucesso", "Seus dados foram editados com sucesso.");
		} else {
			model.addAttribute("falha", "Sua senha não confere, tente novamente.");
		}
		return "paciente/cadastro";
	}	
	
	@GetMapping("/curso/dados")
	public String curso(Curso curso) {
		
		return "paciente/curso";
	}
	
	@PostMapping("/curso/salvar")
	public String salvarCurso(Curso curso, RedirectAttributes attr) {
		service.salvarCurso(curso);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");		
		return "redirect:/pacientes/curso/dados";
	}
			
	@GetMapping("/curso/datatables/server")
	public ResponseEntity<?> getEspecialidades(HttpServletRequest http){
		return ResponseEntity.ok(service.buscarCursos(http));
	}
	
	@GetMapping("/curso/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model){
		model.addAttribute("curso", service.buscarPorId(id));
		return "paciente/curso";
	}
	
	@GetMapping("/curso/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, RedirectAttributes attr){
		service.remover(id);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
		return "redirect:/pacientes/curso/dados";
	}
	
	@GetMapping("/curso/listar")
	public ResponseEntity<?> getCursos() {
		
		return ResponseEntity.ok(service.buscarCursos());
	}
}
