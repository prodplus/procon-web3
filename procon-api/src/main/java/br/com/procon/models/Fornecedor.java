package br.com.procon.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import br.com.procon.models.auxiliares.Endereco;
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
public class Fornecedor implements Serializable {

	private static final long serialVersionUID = -892373347989897798L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false, unique = true)
	@NotBlank(message = "o nome fantasia é obrigatório!")
	@EqualsAndHashCode.Include
	private String fantasia;
	private String razaoSocial;
	@Column(length = 20)
	private String cnpj;
	@Email(message = "email inválido!")
	private String email;
	@Embedded
	private Endereco endereco;
	@ElementCollection
	@CollectionTable(name = "fones_f", joinColumns = @JoinColumn(name = "forn_id"))
	@Column(name = "fone", length = 20, nullable = false)
	private Set<String> fones = new HashSet<>();

}
