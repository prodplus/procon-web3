package br.com.procon.services;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.procon.models.Configuracao;
import br.com.procon.repositories.ConfiguracaoRepository;

/**
 * 
 * @author Marlon F. Garcia
 *
 */
@Service
public class ConfiguracaoService {

	@Autowired
	private ConfiguracaoRepository configRepository;

	public Configuracao salvar(Configuracao config) {
		try {
			config.setId(1);
			return this.configRepository.save(config);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Configuracao buscar() {
		try {
			return this.configRepository.findById(1)
					.orElse(new Configuracao(1, "", "", "", new HashSet<>(), "", ""));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

}
