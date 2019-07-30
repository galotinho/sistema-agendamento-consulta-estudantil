package br.edu.ifbaiano.cae.agendamento.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.edu.ifbaiano.cae.agendamento.domain.PerfilTipo;
import br.edu.ifbaiano.cae.agendamento.service.UsuarioService;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	private static final String ADMIN = PerfilTipo.ADMIN.getDesc();
	private static final String PROFISSIONAL = PerfilTipo.PROFISSIONAL.getDesc();
	private static final String PACIENTE = PerfilTipo.PACIENTE.getDesc();
	
	@Autowired
	private UsuarioService service;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http.authorizeRequests()
		 .antMatchers("/webjars/**", "/css/**", "/js/**", "/image/**").permitAll()
		 .antMatchers("/", "/home").permitAll()
		 .antMatchers("/u/novo/cadastro", "/u/cadastro/realizado", "/u/cadastro/paciente/salvar").permitAll()
		 .antMatchers("/u/confirmacao/cadastro").permitAll()
		 .antMatchers("/u/p/**").permitAll()
		 
		 //acessos privados para admin
		 .antMatchers("/u/editar/senha", "/u/confirmar/senha").hasAnyAuthority(PACIENTE, PROFISSIONAL)
		 .antMatchers("/u/**").hasAuthority(ADMIN)
		 
		 //acessos privados para profissionais
		 .antMatchers("/profissionais/especialidade/titulo/*", "/profissionais/horarios/hora/disponivel/*/*").hasAnyAuthority(PACIENTE, PROFISSIONAL)
		 .antMatchers("/profissionais/dados", "/profissionais/salvar", "/profissionais/editar").hasAnyAuthority(ADMIN,PROFISSIONAL)
		 .antMatchers("/profissionais/**").hasAuthority(PROFISSIONAL)
		 
		//acessos privados para pacientes
		 .antMatchers("/pacientes/curso/listar").hasAnyAuthority(PACIENTE, ADMIN)
		 .antMatchers("/pacientes/curso/**").hasAuthority(ADMIN)		 
		 .antMatchers("/pacientes/**").hasAuthority(PACIENTE)
		 
		//acessos privados para especialidades
		 .antMatchers("/especialidades/datatables/server/profissional/*").hasAnyAuthority(ADMIN,PROFISSIONAL)
		 .antMatchers("/especialidades/titulo").hasAnyAuthority(ADMIN,PROFISSIONAL,PACIENTE)
		 .antMatchers("/especialidades/**").hasAuthority(ADMIN)
		 
		 .anyRequest().authenticated()
		 .and()
			.formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/", true)
			.failureUrl("/login-error")
			.permitAll()
		.and()
			.logout()
			.logoutSuccessUrl("/")
		.and()
			.exceptionHandling()
			.accessDeniedPage("/acesso-negado")
		 .and()
		 	.rememberMe()
		 	.rememberMeCookieName("cae-atendimento")  
		    .tokenValiditySeconds(604800);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(service).passwordEncoder(new BCryptPasswordEncoder());
	}

	
	
}
