package br.com.procon.configs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.procon.models.dtos.TokenDto;
import br.com.procon.models.forms.LoginForm;

/**
 * 
 * @author Marlon F. Garcia
 *
 */
@Service
public class AutenticacaoService {

	@Autowired
	private AuthenticationManager authManager;
	@Autowired
	private TokenService tokenService;

	public TokenDto autenticar(LoginForm form) {
		try {
			UsernamePasswordAuthenticationToken login = form.converter();
			Authentication authentication = authManager.authenticate(login);
			String token = this.tokenService.geraToken(authentication);
			return new TokenDto(token, "Bearer");
		} catch (AuthenticationException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"erro na autenticação do token", e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"erro na autenticação do token", e.getCause());
		}
	}

}
