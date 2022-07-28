package br.com.procon.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.procon.models.Configuracao;
import br.com.procon.services.ConfiguracaoService;

/**
 * 
 * @author Marlon F. Garcia
 *
 */
@RestController
@RequestMapping("/configuracoes")
@CrossOrigin("http://localhost:4200")
public class ConfiguracaoController {
	
	@Autowired
	private ConfiguracaoService configService;
	
	@PostMapping
	public ResponseEntity<Configuracao> salvar(@RequestBody Configuracao config) {
		return ResponseEntity.ok(this.configService.salvar(config));
	}
	
	@GetMapping
	public ResponseEntity<Configuracao> buscar() {
		return ResponseEntity.ok(this.configService.buscar());
	}

}
