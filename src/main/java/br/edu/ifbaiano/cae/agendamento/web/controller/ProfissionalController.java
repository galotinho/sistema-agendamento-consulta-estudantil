package br.edu.ifbaiano.cae.agendamento.web.controller;

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

import br.edu.ifbaiano.cae.agendamento.domain.Profissional;
import br.edu.ifbaiano.cae.agendamento.domain.Usuario;
import br.edu.ifbaiano.cae.agendamento.service.ProfissionalService;
import br.edu.ifbaiano.cae.agendamento.service.UsuarioService;

@Controller
@RequestMapping("profissionais")
public class ProfissionalController {

	@Autowired
	private ProfissionalService service;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("/dados")
	public String abrirPorProfissional(Profissional profissional, ModelMap model, @AuthenticationPrincipal User user) {
		if(profissional.hasNotId()) {
			profissional = service.buscarPorEmail(user.getUsername());
			model.addAttribute("profissional", profissional);
		}
		return "profissional/cadastro";
	}
	
	@PostMapping("/salvar")
	public String salvarProfissional(Profissional profissional, RedirectAttributes attr, @AuthenticationPrincipal User user) {
		
		if(profissional.hasNotId() && profissional.getUsuario().hasNotId()) {
			Usuario usuario = usuarioService.buscarPorEmail(user.getUsername());
			profissional.setUsuario(usuario);
		}
		service.salvar(profissional);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
		attr.addFlashAttribute("profissional", profissional);
		return "redirect:/profissionais/dados";
	}
	
	@PostMapping("/editar")
	public String editarProfissional(Profissional profissional, RedirectAttributes attr) {
		service.editar(profissional);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
		attr.addFlashAttribute("profissional", profissional);
		return "redirect:/profissionais/dados";
	}
	
	@GetMapping("/id/{idProf}/excluir/especializacao/{idEsp}")
	public String excluirEspecialidadePeloProfissional(@PathVariable("idProf") Long idProf, 
			@PathVariable("idEsp") Long idEsp, RedirectAttributes attr){
		
		if ( service.existeEspecialidadeAgendada(idProf, idEsp) ) {
			attr.addFlashAttribute("falha", "Existe consultas agendadas, exclusão negada.");
		} else {		
			service.excluirEspecialidadePorProfissional(idProf, idEsp);
			attr.addFlashAttribute("sucesso", "Especialidade removida com sucesso.");
		}
				
		return "redirect:/profissionais/dados";
	}
	
	// buscar profissionais por especialidade via ajax
		@GetMapping("/especialidade/titulo/{titulo}")
		public ResponseEntity<?> getProfissionaisPorEspecialidade(@PathVariable("titulo") String titulo) {
			return ResponseEntity.ok(service.buscarProfissionaisPorEspecialidade(titulo));
		}
	
}
