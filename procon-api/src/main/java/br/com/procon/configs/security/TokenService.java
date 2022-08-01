package br.com.procon.configs.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.procon.models.Usuario;
import br.com.procon.repositories.UsuarioRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * 
 * @author Marlon F. Garcia
 *
 */
@Service
public class TokenService {

	private Long expiration = 6048000000l;
	@Value("${jwt.secret}")
	private String secret;
	@Autowired
	private UsuarioRepository usuarioRepository;

	public String geraToken(Authentication auth) {
		try {
			Usuario logado = new Usuario();
			if (auth.getPrincipal() instanceof User) {
				User user = (User) auth.getPrincipal();
				logado = this.usuarioRepository.findByEmail(user.getUsername()).orElse(null);
			} else {
				logado = (Usuario) auth.getPrincipal();
			}

			Date hoje = new Date();
			Date dataExpiracao = new Date(hoje.getTime() + this.expiration);

			return Jwts.builder().setIssuer(logado.getNome()).setSubject(logado.getId().toString())
					.setIssuedAt(hoje).claim("id", logado.getId())
					.claim("email", logado.getUsername())
					.claim("perfil", logado.getPerfil().getRole()).claim("nome", logado.getNome())
					.setExpiration(dataExpiracao).signWith(SignatureAlgorithm.HS256, this.secret)
					.compact();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"erro na geração do token!", e.getCause());
		}
	}

	public boolean isTokenValid(String token) {
		try {
			if (token != null && token.length() > 0) {
				Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
				return true;
			} else {
				return false;
			}
		} catch (ExpiredJwtException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"erro na geração do token!", e.getCause());
		} catch (UnsupportedJwtException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"erro na geração do token!", e.getCause());
		} catch (MalformedJwtException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"erro na geração do token!", e.getCause());
		} catch (SignatureException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"erro na geração do token!", e.getCause());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"erro na geração do token!", e.getCause());
		}
	}

	public Integer getIdUsuario(String token) {
		Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		return Integer.valueOf(claims.getSubject());
	}

}
