package br.com.procon.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.procon.models.Log;
import br.com.procon.models.Usuario;
import br.com.procon.models.enums.TipoLog;
import br.com.procon.repositories.LogRepository;

/**
 * 
 * @author Marlon F. Garcia
 *
 */
@Service
public class LogService {

	@Autowired
	private LogRepository logRepository;

	public Log salvar(LocalDateTime data, String log, TipoLog tipo) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Usuario usuario = (Usuario) auth.getPrincipal();
			return this.logRepository.save(new Log(null, data, usuario, log, tipo));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<Log> listar(LocalDateTime inicio, LocalDateTime fim) {
		return this.logRepository.findAllByDataBetween(inicio, fim,
				Sort.by(Sort.Direction.ASC, "data"));
	}

	public void excluir(Integer id) {
		try {
			this.logRepository.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

}
