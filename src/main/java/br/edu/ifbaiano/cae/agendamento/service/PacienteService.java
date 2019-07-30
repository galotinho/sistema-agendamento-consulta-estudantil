package br.edu.ifbaiano.cae.agendamento.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifbaiano.cae.agendamento.datatables.Datatables;
import br.edu.ifbaiano.cae.agendamento.datatables.DatatablesColunas;
import br.edu.ifbaiano.cae.agendamento.domain.Curso;
import br.edu.ifbaiano.cae.agendamento.domain.Horario;
import br.edu.ifbaiano.cae.agendamento.domain.Paciente;
import br.edu.ifbaiano.cae.agendamento.repository.CursoRepository;
import br.edu.ifbaiano.cae.agendamento.repository.PacienteRepository;

@Service
public class PacienteService {

	@Autowired
	private PacienteRepository repository;
	@Autowired
	private CursoRepository cursoRepository;
	@Autowired
	private Datatables dataTables;
	
	@Transactional(readOnly = true)
	public Paciente buscarPorUsuarioEmail(String email) {
		
		return repository.findByUsuarioEmail(email).orElse(new Paciente());
	}

	@Transactional(readOnly = false)
	public void salvar(Paciente paciente) {
		
		repository.save(paciente);		
	}

	@Transactional(readOnly = false)
	public void editar(Paciente paciente) {
		Paciente p2 = repository.findById(paciente.getId()).get();
		p2.setNome(paciente.getNome());
		p2.setDtNascimento(paciente.getDtNascimento());		
	}

	@Transactional(readOnly = false)
	public void salvarCurso(Curso curso) {
		cursoRepository.save(curso);		
	}

	@Transactional(readOnly=true)
	public Map<String,Object> buscarCursos(HttpServletRequest http) {
				
			dataTables.setRequest(http);
			dataTables.setColunas(DatatablesColunas.CURSOS);
			
			Page<?> page;
			
			if(dataTables.getSearch().isEmpty()) {
				page = cursoRepository.findAll(dataTables.getPageable());
			}else {
				page = cursoRepository.findAllByNome(dataTables.getSearch(), dataTables.getPageable());
			}
			
			return dataTables.getResponse(page);
		
	}

	@Transactional(readOnly=true)
	public Curso buscarPorId(Long id) {
		return cursoRepository.findById(id).get();
	}
	
	@Transactional(readOnly=false)
	public void remover(Long id) {
		cursoRepository.deleteById(id);	
		
	}

	@Transactional(readOnly = true)
	public List<Curso> buscarCursos() {
		return cursoRepository.findAll();
	}

}
