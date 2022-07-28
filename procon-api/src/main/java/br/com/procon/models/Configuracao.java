package br.com.procon.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Entity
public class Configuracao implements Serializable {

	private static final long serialVersionUID = 569530053260890934L;
	@Id
	@JsonIgnore
	private Integer id = 1;
	private String descricaoProcon;
	private String nomeProcon;
	private String endereco;
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<String> fones = new HashSet<>();
	private String email;
	private String diretoria;

}
