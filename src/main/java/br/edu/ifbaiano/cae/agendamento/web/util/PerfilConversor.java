package br.edu.ifbaiano.cae.agendamento.web.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.edu.ifbaiano.cae.agendamento.domain.Perfil;

@Component
public class PerfilConversor implements Converter<String[], List<Perfil>>{

	@Override
	public List<Perfil> convert(String[] source) {
		List<Perfil> perfis = new ArrayList<>();
		
		for(int i=0; i < source.length; i++) {
			String id = source[i];
			
			if(!id.equals("0")) {
				Long idPerfil = Long.parseLong(id);
				Perfil perfil = new Perfil(idPerfil);
				perfis.add(perfil);
			}			
		}
		return perfis;
	}

}
