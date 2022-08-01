package br.com.procon.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Perfil implements Serializable, GrantedAuthority {

	private static final long serialVersionUID = 6614663084292025595L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false, unique = true, length = 20)
	@NotBlank(message = "a regra é obrigatória!")
	@EqualsAndHashCode.Include
	private String role;
	@Column(nullable = false, length = 50)
	@NotBlank(message = "a descrição é obrigatória!")
	private String descricao;

	@Override
	@JsonIgnore
	public String getAuthority() {
		return this.role;
	}

}
