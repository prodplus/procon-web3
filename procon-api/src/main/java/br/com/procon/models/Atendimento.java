package br.com.procon.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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
public class Atendimento implements Serializable {

	private static final long serialVersionUID = 8080046802053248573L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@OrderColumn
	private List<Consumidor> consumidores = new ArrayList<>();
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@OrderColumn
	private List<Fornecedor> fornecedores = new ArrayList<>();
	@Column(nullable = false)
	private LocalDate data;
	@Lob
	@Basic(fetch = FetchType.EAGER)
	private String relato;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Usuario atendente;

}
