package br.com.procon.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import br.com.procon.models.auxiliares.Movimento;
import br.com.procon.models.enums.Situacao;
import br.com.procon.models.enums.TipoProcesso;
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
public class Processo implements Serializable {

	private static final long serialVersionUID = 5443898870084380560L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private TipoProcesso tipo;
	@EqualsAndHashCode.Include
	@Column(nullable = false, unique = true, length = 10)
	private String autos;
	@Column(nullable = false)
	private LocalDate data;
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@OrderColumn
	private List<Consumidor> consumidores = new ArrayList<>();
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@OrderColumn
	private List<Consumidor> representantes = new ArrayList<>();
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@OrderColumn
	private List<Fornecedor> fornecedores = new ArrayList<>();
	@ElementCollection
	@OrderColumn
	private List<Movimento> movimentacao = new ArrayList<>();
	@Lob
	@Basic(fetch = FetchType.EAGER)
	private String relato;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private Situacao situacao;
	@ManyToOne
	private Usuario atendente;

}
