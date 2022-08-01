package br.com.procon.configs.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import br.com.procon.models.Usuario;
import br.com.procon.repositories.UsuarioRepository;

/**
 * 
 * @author Marlon F. Garcia
 *
 */
public class TokenFilter extends OncePerRequestFilter {

	private TokenService tokenService;
	private UsuarioRepository usuarioRepository;

	public TokenFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
		this.tokenService = tokenService;
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {
		String token = recuperarToken(request);
		boolean valido = this.tokenService.isTokenValid(token);
		if (valido)
			autenticarCliente(token);
		
		filterChain.doFilter(request, response);
	}

	private Usuario autenticarCliente(String token) {
		try {
			Integer idUsuario = this.tokenService.getIdUsuario(token);
			Usuario usuario = this.usuarioRepository.findById(idUsuario).get();
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
					usuario, usuario.getPassword(), usuario.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
			return usuario;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"erro na autenticação do token", e.getCause());
		}
	}

	private String recuperarToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (token == null || token.isBlank() || !token.startsWith("Bearer"))
			return null;
		return token.substring(7, token.length());
	}

}
