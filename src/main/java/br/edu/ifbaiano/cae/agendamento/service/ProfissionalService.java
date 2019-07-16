package br.edu.ifbaiano.cae.agendamento.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifbaiano.cae.agendamento.domain.Profissional;
import br.edu.ifbaiano.cae.agendamento.repository.ProfissionalRepository;


@Service
public class ProfissionalService {

	@Autowired
	private ProfissionalRepository repository;
	
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
	
}
