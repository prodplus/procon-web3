package br.com.procon.models.forms;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.procon.models.Usuario;
import br.com.procon.repositories.PerfilRepository;
import br.com.procon.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author Marlon F. Garcia
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioForm implements Serializable {

	private static final long serialVersionUID = -5579352740629660827L;

	private Integer id;
	@NotBlank(message = "o nome é obrigatório!")
	private String nome;
	@NotBlank(message = "o email é obrigatório!")
	@Email(message = "email inválido!")
	private String email;
	@NotBlank(message = "a senha é obrigatória!")
	@Size(max = 10, message = "a senha deve possuir no máximo 10 caracteres!")
	private String password;
	@NotBlank(message = "o perfil é obrigatório!")
	private String perfil;
	@NotNull(message = "campo obrigatório!")
	private boolean ativo;

	public Usuario conveter(UsuarioRepository usuarioRepository, PerfilRepository perfilRepository,
			BCryptPasswordEncoder encoder) {
		return usuarioRepository.findById(this.getId()).map(u -> {
			u.setNome(this.getNome());
			u.setAtivo(this.isAtivo());
			u.setEmail(this.getEmail());
			u.setPassword(encoder.encode(this.getPassword()));
			u.setPerfil(perfilRepository.findByRole(this.getPerfil()).get());
			return u;
		}).orElse(new Usuario(null, this.getNome(), this.getEmail(),
				encoder.encode(this.getPassword()),
				perfilRepository.findByRole(this.getPerfil()).get(), this.isAtivo()));
	}

}
