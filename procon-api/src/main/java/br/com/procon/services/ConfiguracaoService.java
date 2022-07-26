package br.com.procon.services;

import java.time.LocalDateTime;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.procon.models.Configuracao;
import br.com.procon.models.enums.TipoLog;
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
	@Autowired
	private LogService logService;

	public Configuracao salvar(Configuracao config) {
		try {
			config.setId(1);
			this.logService.salvar(LocalDateTime.now(), "Configurações alteradas",
					TipoLog.ATUALIZACAO);
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
