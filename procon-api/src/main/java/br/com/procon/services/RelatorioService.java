package br.com.procon.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.procon.models.Atendimento;
import br.com.procon.models.Configuracao;
import br.com.procon.reports.InicialAtendimento;

/**
 * 
 * @author Marlon F. Garcia
 *
 */
@Service
public class RelatorioService {

	@Autowired
	private ConfiguracaoService configuracaoService;
	@Autowired
	private AtendimentoService atendimentoService;

	public InputStreamResource atendimentoIni(Integer id) {
		try {
			Atendimento atendimento = this.atendimentoService.buscarI(id);
			Configuracao config = this.configuracaoService.buscar();
			return new InputStreamResource(InicialAtendimento.gerar(atendimento, config));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

}
