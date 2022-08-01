package br.com.procon.models;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
public class Usuario implements Serializable, UserDetails {

	private static final long serialVersionUID = -776518492774298727L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false)
	private String nome;
	@Column(nullable = false, unique = true)
	@EqualsAndHashCode.Include
	private String email;
	@Column(nullable = false)
	private String password;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Perfil perfil;
	@Column(nullable = false)
	private boolean ativo = true;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(this.perfil);
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.ativo;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.ativo;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.ativo;
	}

	@Override
	public boolean isEnabled() {
		return this.ativo;
	}

}
