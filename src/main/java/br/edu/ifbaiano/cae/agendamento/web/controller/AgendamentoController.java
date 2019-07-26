package br.edu.ifbaiano.cae.agendamento.web.controller;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifbaiano.cae.agendamento.domain.Agendamento;
import br.edu.ifbaiano.cae.agendamento.domain.Especialidade;
import br.edu.ifbaiano.cae.agendamento.domain.Horario;
import br.edu.ifbaiano.cae.agendamento.domain.Paciente;
import br.edu.ifbaiano.cae.agendamento.domain.PerfilTipo;
import br.edu.ifbaiano.cae.agendamento.service.AgendamentoService;
import br.edu.ifbaiano.cae.agendamento.service.EspecialidadeService;
import br.edu.ifbaiano.cae.agendamento.service.PacienteService;
import br.edu.ifbaiano.cae.agendamento.service.ProfissionalService;

@Controller
@RequestMapping("agendamentos")
public class AgendamentoController {
	
	@Autowired
	private AgendamentoService service;
	@Autowired
	private PacienteService pacienteService;
	@Autowired
	private EspecialidadeService especialidadeService;	
	@Autowired
	private ProfissionalService profissionalService;

	// abre a pagina de agendamento de consultas 
	@PreAuthorize("hasAnyAuthority('PACIENTE', 'PROFISSIONAL')")
	@GetMapping({"/agendar"})
	public String agendarConsulta(Agendamento agendamento) {

		return "agendamento/cadastro";		
	}
	
	// salvar uma consulta agendada
	@PreAuthorize("hasAuthority('PACIENTE')")
	@PostMapping({"/salvar"})
	public ModelAndView salvar(Agendamento agendamento, RedirectAttributes attr, @AuthenticationPrincipal User user) {
		Paciente paciente = pacienteService.buscarPorUsuarioEmail(user.getUsername());
		String titulo = agendamento.getEspecialidade().getTitulo();
		Especialidade especialidade = especialidadeService
				.buscarPorTitulos(new String[] {titulo})
				.stream().findFirst().get();
		agendamento.setEspecialidade(especialidade);
		agendamento.setPaciente(paciente);
		agendamento.setHorario(profissionalService.buscarHorario(Long.valueOf(agendamento.getHorarioS())));
				
		if(agendamento.getPaciente().getNome() == null) {
			ModelAndView model = new ModelAndView("error");
			model.addObject("status", 500);
			model.addObject("error", "Área Restrita");
			model.addObject("message", "Preencha seus DADOS CADASTRAIS antes de agendar uma consulta.");
			return model;
		}
		service.salvar(agendamento);
		attr.addFlashAttribute("sucesso", "Sua consulta foi agendada com sucesso.");
			
		
		return new ModelAndView("redirect:/agendamentos/agendar");		
	}
	
	// abrir pagina de historico de agendamento do paciente
	@PreAuthorize("hasAnyAuthority('PACIENTE', 'PROFISSIONAL')")
	@GetMapping({"/historico/paciente", "/historico/consultas"})
	public String historico() {

		return "agendamento/historico-paciente";
	}
	
	// localizar o historico de agendamentos por usuario logado
	@PreAuthorize("hasAnyAuthority('PACIENTE', 'PROFISSIONAL')")
	@GetMapping("/datatables/server/historico")
	public ResponseEntity<?> historicoAgendamentosPorPaciente(HttpServletRequest request, @AuthenticationPrincipal User user) {
		if (user.getAuthorities().contains(new SimpleGrantedAuthority(PerfilTipo.PACIENTE.getDesc()))) {
			
			return ResponseEntity.ok(service.buscarHistoricoPorPacienteEmail(user.getUsername(), request));
		}
		
		if (user.getAuthorities().contains(new SimpleGrantedAuthority(PerfilTipo.PROFISSIONAL.getDesc()))) {
			
			return ResponseEntity.ok(service.buscarHistoricoPorProfissionalEmail(user.getUsername(), request));
		}		
		
		return ResponseEntity.notFound().build();
	}
	
	// localizar agendamento pelo id e envia-lo para a pagina de cadastro
	@PreAuthorize("hasAnyAuthority('PACIENTE', 'PROFISSIONAL')")
	@GetMapping("/editar/consulta/{id}") 
	public String preEditarConsultaPaciente(@PathVariable("id") Long id, 
										    ModelMap model, @AuthenticationPrincipal User user) {
		
		Agendamento agendamento = service.buscarPorIdEUsuario(id, user.getUsername());
		model.addAttribute("agendamento", agendamento);
		
		return "agendamento/cadastro";
	}
	
	@PreAuthorize("hasAnyAuthority('PACIENTE', 'PROFISSIONAL')")
	@PostMapping("/editar")
	public String editarConsulta(Agendamento agendamento, RedirectAttributes attr, @AuthenticationPrincipal User user) {
		String titulo = agendamento.getEspecialidade().getTitulo();
		Especialidade especialidade = especialidadeService
				.buscarPorTitulos(new String[] {titulo})
				.stream().findFirst().get();
		agendamento.setEspecialidade(especialidade);
		Horario horario;
		if(agendamento.getHorarioS() != null) {
			horario = profissionalService.buscarHorario(Long.valueOf(agendamento.getHorarioS()));
		}
		else {
			horario = profissionalService.buscarHorario(agendamento.getHorario().getId());
		}
		service.editar(agendamento, user.getUsername(), horario);
		attr.addFlashAttribute("sucesso", "Sua consulta foi alterada com sucesso.");
		return "redirect:/agendamentos/agendar";
	}
	
	
	@PreAuthorize("hasAuthority('PACIENTE')")
	@GetMapping("/excluir/consulta/{id}")
	public String excluirConsulta(@PathVariable("id") Long id, RedirectAttributes attr) {
		service.remover(id);
		attr.addFlashAttribute("sucesso", "Consulta excluída com sucesso.");
		return "redirect:/agendamentos/historico/paciente";
	}

}
