package br.edu.ifbaiano.cae.agendamento.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifbaiano.cae.agendamento.datatables.Datatables;
import br.edu.ifbaiano.cae.agendamento.datatables.DatatablesColunas;
import br.edu.ifbaiano.cae.agendamento.domain.Especialidade;
import br.edu.ifbaiano.cae.agendamento.repository.EspecialidadeRepository;

@Service
public class EspecialidadeService {
	
	@Autowired
	private EspecialidadeRepository repository;

	@Autowired
	private Datatables dataTables;
	
	@Transactional(readOnly=false)
	public void salvar(Especialidade especialidade) {
		repository.save(especialidade);		
	}

	@Transactional(readOnly=true)
	public Map<String,Object> buscarEspecialidades(HttpServletRequest http) {
		
		dataTables.setRequest(http);
		dataTables.setColunas(DatatablesColunas.ESPECIALIDADES);
		
		Page<?> page;
		
		if(dataTables.getSearch().isEmpty()) {
			page = repository.findAll(dataTables.getPageable());
		}else {
			page = repository.findAllByTitulo(dataTables.getSearch(), dataTables.getPageable());
		}
		
		return dataTables.getResponse(page);
	}

	@Transactional(readOnly=true)
	public Especialidade buscarPorId(Long id) {
		return repository.findById(id).get();
	}

	@Transactional(readOnly=false)
	public void remover(Long id) {
		repository.deleteById(id);		
	}
	
	@Transactional(readOnly=true)
	public List<String> buscarEspecialidadesPorTermo(String termo) {
		 return repository.findByTermo(termo);
	}

	@Transactional(readOnly=true)
	public Set<Especialidade> buscarPorTitulos(String[] titulos) {
		
		return repository.findByTitulo(titulos);
	}

	@Transactional(readOnly=true)
	public Map<String, Object> buscarEspecialidadesPorMedico(Long id, HttpServletRequest http) {
		dataTables.setRequest(http);
		dataTables.setColunas(DatatablesColunas.ESPECIALIDADES);
		
		Page<Especialidade> page = repository.findByIdProfissional(id, dataTables.getPageable());
				
		return dataTables.getResponse(page);
	}

	@Transactional(readOnly=true)
	public List<Especialidade> buscarTodas() {

		return repository.findAll();
	}

}
