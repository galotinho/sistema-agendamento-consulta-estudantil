package br.edu.ifbaiano.cae.agendamento.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifbaiano.cae.agendamento.datatables.Datatables;
import br.edu.ifbaiano.cae.agendamento.datatables.DatatablesColunas;
import br.edu.ifbaiano.cae.agendamento.domain.Data;
import br.edu.ifbaiano.cae.agendamento.domain.Horario;
import br.edu.ifbaiano.cae.agendamento.domain.Profissional;
import br.edu.ifbaiano.cae.agendamento.repository.DataRepository;
import br.edu.ifbaiano.cae.agendamento.repository.ProfissionalRepository;



@Service
public class ProfissionalService {

	@Autowired
	private ProfissionalRepository repository;
	
	@Autowired
	private DataRepository dataRepository;
	
	@Autowired
	private Datatables datatables;
	
	@Transactional(readOnly=true)
	public Profissional buscarPorId(Long id) {
		return repository.findByUsuarioId(id).orElse(new Profissional());
	}
	
	@Transactional(readOnly=false)
	public void salvar(Profissional profissional) {
		repository.save(profissional);		
	}

	@Transactional(readOnly=false)
	public void editar(Profissional profissional) {
		Profissional p = repository.findById(profissional.getId()).get();
		p.setConselho(profissional.getConselho());
		p.setDtInscricao(profissional.getDtInscricao());
		p.setNome(profissional.getNome());
		
		if(!profissional.getEspecialidades().isEmpty()) {
			p.getEspecialidades().addAll(profissional.getEspecialidades());
		}
		
		repository.save(p);
	}
	
	@Transactional(readOnly=true)
	public Profissional buscarPorEmail(String email) {

		return repository.findByEmail(email).orElse(new Profissional());
	}

	@Transactional(readOnly=false)
	public void excluirEspecialidadePorProfissional(Long idProf, Long idEsp) {
		Profissional profissional = repository.findById(idProf).get();
		profissional.getEspecialidades().removeIf(e -> e.getId().equals(idEsp));		
	}
	
	@Transactional(readOnly = true)
	public List<Profissional> buscarProfissionaisPorEspecialidade(String titulo) {
		
		return repository.findByProfissionaisPorEspecialidade(titulo);
	}

	@Transactional(readOnly = true)
	public boolean existeEspecialidadeAgendada(Long idProf, Long idEsp) {
		
		return repository.hasEspecialidadeAgendada(idProf, idEsp).isPresent();
	}

	@Transactional(readOnly = false)
	public void salvarHorarioDisponivel(Data dataDisponivel) {
		
		dataRepository.save(dataDisponivel);
	}

	@Transactional(readOnly = true)
	public List<Horario> buscarHorarios() {
		
		return repository.findHorarios();
	}

	@Transactional(readOnly = true)
	public Horario buscarHorario(Long id) {
		// TODO Auto-generated method stub
		return repository.findByIdHorario(id);
	}

	@Transactional(readOnly = true)
	public Map<String, Object> buscarHorariosPorProfissionalEmail(String email, HttpServletRequest request) {
		
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.HORARIOS);
		
		Page<?> page = dataRepository.findAll(datatables.getPageable());
		
		
		return datatables.getResponse(page);
	}

	@Transactional(readOnly = false)
	public void removerDataDisponivel(Long id) {
		dataRepository.deleteById(id);
		
	}

	@Transactional(readOnly = true)
	public Optional<Data> buscarDataPorId(Long id) {
		return dataRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public List<Horario> buscarHorariosDisponibilizados(String email, LocalDate data) {

		return repository.findHorariosDisponibilizados(email, data);
	}

	@Transactional(readOnly = false)
	public void editarHorarioDisponivel(Data dataDisponibilizada, Profissional profissional,
			List<Horario> horarios) {
		Data dt = dataRepository.findById(dataDisponibilizada.getId()).get();
		dt.setProfissional(profissional);
		dt.setHorarios(horarios);
		
	}
	@Transactional(readOnly = true)
	public List<Horario> buscarHorariosDisponibilizadosPorProfissional(Long idProfissional, LocalDate data) {
		
		return repository.findHorariosDisponibilizadosPorProfissional(idProfissional, data);
	}	
	
	@Transactional(readOnly = true)
	public List<Horario> buscarHorariosIdDataNotAgendado(Long idProfissional, LocalDate data) {
		
		return repository.findByProfissionalIdAndDataNotHorarioAgendado(idProfissional, data);
	}

	@Transactional(readOnly = true)
	public String buscarDatasDisponiveis(Long idProfissional) {
		List<LocalDate> datasDisponiveis = repository.findAllDatesAvailables(idProfissional);
		
		String datas = "";
		String formato = "dd/MM/yyyy";
		DateTimeFormatter dataFormatada = DateTimeFormatter.ofPattern(formato);
		
		for(int i = 0; i<datasDisponiveis.size(); i++) {
			String d = datasDisponiveis.get(i).format(dataFormatada);
			
			if(i != (datasDisponiveis.size()-1) ) {
				datas += d+",";	
			}else {
				datas += d;
			}					
		}
		return datas;
	}
	
}
