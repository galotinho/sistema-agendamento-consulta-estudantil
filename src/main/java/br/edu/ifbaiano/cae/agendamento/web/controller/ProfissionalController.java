package br.edu.ifbaiano.cae.agendamento.web.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

import br.edu.ifbaiano.cae.agendamento.domain.Data;
import br.edu.ifbaiano.cae.agendamento.domain.Horario;
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
	
	@GetMapping("/horarios")
	public String cadastrarHorario(ModelMap model) {
		
		model.addAttribute("dataDisponibilizada", new Data());
		return "profissional/horario";
	}
	
	@GetMapping("/horarios/hora/disponivel")
	public ResponseEntity<?> getHorarios() {
		
		return ResponseEntity.ok(service.buscarHorarios());
	}
	
	@GetMapping("/horarios/hora/disponivel/{data}")
	public ResponseEntity<?> getHorariosDisponibilizados(@PathVariable("data") String data, @AuthenticationPrincipal User user) {
		
		return ResponseEntity.ok(service.buscarHorariosDisponibilizados(user.getUsername(), LocalDate.parse(data)));
	}
	
	@GetMapping("/horarios/hora/disponivel/{data}/{idProfissional}")
	public ResponseEntity<?> getHorariosDisponibilizados(@PathVariable("data") String data, @PathVariable("idProfissional") Long idProfissional) {
		
		List<Horario> horariosDisponiveis = service.buscarHorariosDisponibilizadosPorProfissional(idProfissional, LocalDate.parse(data));
		List<Horario> horariosMarcados = service.buscarHorariosIdDataNotAgendado(idProfissional, LocalDate.parse(data));
		horariosDisponiveis.removeAll(horariosMarcados);
		
		return ResponseEntity.ok(horariosDisponiveis);
	}
		
	@PostMapping("/horarios/salvar")
	public String salvarHorario(Data dataDisponibilizada, RedirectAttributes attr, @AuthenticationPrincipal User user) {
		
		dataDisponibilizada.setProfissional(service.buscarPorEmail(user.getUsername()));
		dataDisponibilizada.setHorarios(converter(dataDisponibilizada.getHorarioLista()));
		
		service.salvarHorarioDisponivel(dataDisponibilizada);
		
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
		attr.addFlashAttribute("dataDisponivel", dataDisponibilizada);
		
		return "redirect:/profissionais/horarios";
	}	
	
	@PostMapping("/horarios/editar")
	public String editarHorario(Data dataDisponibilizada, RedirectAttributes attr, @AuthenticationPrincipal User user) {
		
	    service.editarHorarioDisponivel(dataDisponibilizada, 
				service.buscarPorEmail(user.getUsername()), 
				converter(dataDisponibilizada.getHorarioLista()));
		
		attr.addFlashAttribute("sucesso", "Alteração realizada com sucesso!");
		attr.addFlashAttribute("dataDisponivel", dataDisponibilizada);
		
		return "redirect:/profissionais/horarios";
	}
		
	@GetMapping("/datatables/server/disponibilidade")
	public ResponseEntity<?> historicoDisponibilidadeHorarios(HttpServletRequest request, @AuthenticationPrincipal User user) {
		
		return ResponseEntity.ok(service.buscarHorariosPorProfissionalEmail(user.getUsername(), request));
		
	}
	
	@GetMapping("/excluir/disponibilidade/{id}")
	public String excluirDisponibilidade(@PathVariable("id") Long id, RedirectAttributes attr) {
		service.removerDataDisponivel(id);
		attr.addFlashAttribute("sucesso", "Data excluída com sucesso.");
		return "redirect:/profissionais/horarios";
	}
	
	@GetMapping("/editar/disponibilidade/{id}") 
	public String preEditarDisponibilidade(@PathVariable("id") Long id, 
										    ModelMap model, @AuthenticationPrincipal User user) {
		
		Data dataDisponibilizada = service.buscarDataPorId(id).get();
		
		model.addAttribute("dataDisponibilizada", dataDisponibilizada);
		return "profissional/horario";
	}	
	
	private List<Horario> converter(String[] horarios){
		
		List<Horario> lista = new ArrayList<Horario>();
		
		for(int i=0; i<horarios.length; i++) {
			Horario h = service.buscarHorario(Long.parseLong(horarios[i]));
			lista.add(h);
		}
		
		return lista;
	}
	
}
