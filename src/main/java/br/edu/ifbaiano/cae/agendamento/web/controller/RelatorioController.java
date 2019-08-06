package br.edu.ifbaiano.cae.agendamento.web.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifbaiano.cae.agendamento.service.RelatorioService;


@Controller
@RequestMapping("relatorio")
public class RelatorioController {
	
	@Autowired
	private RelatorioService service;

	@GetMapping("/dados")
	public ModelAndView exibirDados() {
		
		LocalDate dataInicial = LocalDate.of(2019, 1, 1);
		LocalDate dataFinal = LocalDate.now();
		
		ModelAndView mav = new ModelAndView("relatorio/relatorio");
		mav.addObject("relatorio", service.listarTudoPorData(dataInicial, dataFinal));
		mav.addObject("relatorioPorEspecialidade", service.listarTudoPorEspecialidadePorData(dataInicial, dataFinal));
		mav.addObject("relatorioPorCurso", service.listarTudoPorCursoPorData(dataInicial, dataFinal));
		
		return mav;
	}
	
	
	@GetMapping({"/gerar/{dataInicial}/{dataFinal}"})
	public ModelAndView gerarRelatorio( @PathVariable("dataInicial") @DateTimeFormat(iso = ISO.DATE) LocalDate dataInicial, 
			@PathVariable("dataFinal") @DateTimeFormat(iso = ISO.DATE) LocalDate dataFinal, RedirectAttributes attr) {
		
		ModelAndView mav = new ModelAndView("relatorio/relatorio");
		
		mav.addObject("relatorio", service.listarTudoPorData(dataInicial, dataFinal));
		mav.addObject("relatorioPorEspecialidade", service.listarTudoPorEspecialidadePorData(dataInicial, dataFinal));
		mav.addObject("relatorioPorCurso", service.listarTudoPorCursoPorData(dataInicial, dataFinal));
		
		return mav;		
	}
}
