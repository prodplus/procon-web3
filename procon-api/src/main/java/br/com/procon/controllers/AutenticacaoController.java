package br.com.procon.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.procon.configs.security.AutenticacaoService;
import br.com.procon.models.dtos.TokenDto;
import br.com.procon.models.forms.LoginForm;

/**
 * 
 * @author Marlon F. Garcia
 *
 */
@RestController
@RequestMapping("/auth")
public class AutenticacaoController {
	
	@Autowired
	private AutenticacaoService authService;
	
	@PostMapping
	public ResponseEntity<TokenDto> autenticar(@RequestBody LoginForm form) {
		return ResponseEntity.ok(this.authService.autenticar(form));
	}

}
