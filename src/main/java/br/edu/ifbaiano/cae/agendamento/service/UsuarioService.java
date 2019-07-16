package br.edu.ifbaiano.cae.agendamento.service;

import java.util.List;

import java.util.Map;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import br.edu.ifbaiano.cae.agendamento.datatables.Datatables;
import br.edu.ifbaiano.cae.agendamento.datatables.DatatablesColunas;
import br.edu.ifbaiano.cae.agendamento.domain.Perfil;
import br.edu.ifbaiano.cae.agendamento.domain.PerfilTipo;
import br.edu.ifbaiano.cae.agendamento.domain.Usuario;
import br.edu.ifbaiano.cae.agendamento.exception.AcessoNegadoException;
import br.edu.ifbaiano.cae.agendamento.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService{

	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private Datatables dataTables;
	
	@Autowired
	private EmailService emailService;
	
	@Transactional(readOnly=true)
	public Usuario buscarPorEmail(String email) {
		return repository.findByEmail(email);
	}

	@Override @Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = buscarPorEmailEAtivo(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario " + username + " não encontrado."));
		return new User(
				usuario.getEmail(),
				usuario.getSenha(),
				AuthorityUtils.createAuthorityList(getAutorizacoes(usuario.getPerfis()))
				);
	}
	
	//Método para converter uma List em Um Array de String
	
	private String[] getAutorizacoes( List<Perfil> perfis) {
		String[] autorizacoes = new String[perfis.size()];
		for(int i=0; i< autorizacoes.length; i++) {
			autorizacoes[i] = perfis.get(i).getDesc();
		}
		return autorizacoes;
		
	}

	@Transactional(readOnly=true)
	public Map<String,Object> buscarTodos(HttpServletRequest http) {
		
		dataTables.setRequest(http);
		dataTables.setColunas(DatatablesColunas.USUARIOS);
		
		Page<Usuario> page;
		
		if(dataTables.getSearch().isEmpty()) {
			page = repository.findAll(dataTables.getPageable());
		}else {
			page = repository.findByEmailOrPerfil(dataTables.getSearch(), dataTables.getPageable());
		}
		
		return dataTables.getResponse(page);
	}
	
	@Transactional(readOnly=false)
	public void salvarUsuario(Usuario usuario) {
		BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();
		String senha = bCryptEncoder.encode(usuario.getSenha());
		usuario.setSenha(senha);
		
		repository.save(usuario);
		
	}

	public Usuario buscarPorId(Long id) {
		
		return repository.findById(id).get();
	}

	public Usuario buscarporIdEPerfis(Long idUsuario, Long[] perfisId) {

		return repository.findByIdAndPerfis(idUsuario, perfisId)
				.orElseThrow(()-> new UsernameNotFoundException("Usuário Inexistente!"));
	}
	
    public static boolean isSenhaCorreta(String senhaDigitada, String senhaArmazenada) {
		
		return new BCryptPasswordEncoder().matches(senhaDigitada, senhaArmazenada);
	}

	@Transactional(readOnly = false)
	public void alterarSenha(Usuario usuario, String senha) {
		usuario.setSenha(new BCryptPasswordEncoder().encode(senha));
		repository.save(usuario);		
	}
	@Transactional(readOnly = false)
	public void salvarCadastroPaciente(Usuario usuario) throws MessagingException {
		String crypt = new BCryptPasswordEncoder().encode(usuario.getSenha());
		usuario.setSenha(crypt);
		usuario.addPerfil(PerfilTipo.PACIENTE);
		repository.save(usuario);	
		
		emailDeConfirmacaoDeCadastro(usuario.getEmail());
	}
	
	@Transactional(readOnly = true)
	public Optional<Usuario> buscarPorEmailEAtivo(String email) {
		
		return repository.findByEmailAndAtivo(email);
	}
	
	public void emailDeConfirmacaoDeCadastro(String email) throws MessagingException {
		String codigo = Base64Utils.encodeToString(email.getBytes());
		emailService.enviarPedidoDeConfirmacaoDeCadastro(email, codigo);
	}
	
	@Transactional(readOnly = false)
	public void ativarCadastroPaciente(String codigo) {
		String email = new String(Base64Utils.decodeFromString(codigo));
		Usuario usuario = buscarPorEmail(email);
		if (usuario.hasNotId()) {
			throw new AcessoNegadoException("Não foi possível ativar seu cadastro. Entre em "
					+ "contato com o setor.");
		}
		usuario.setAtivo(true);
	}

	@Transactional(readOnly = false)
	public void pedidoRedefinicaoDeSenha(String email) throws MessagingException {
		Usuario usuario = buscarPorEmailEAtivo(email)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario " + email + " não encontrado."));;
		
		String verificador = RandomStringUtils.randomAlphanumeric(6);
		
		usuario.setCodigoVerificador(verificador);
		
		emailService.enviarPedidoRedefinicaoSenha(email, verificador);
	}


}
