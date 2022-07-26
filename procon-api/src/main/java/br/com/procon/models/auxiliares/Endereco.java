package br.com.procon.models.auxiliares;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import br.com.procon.models.enums.UF;
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
@EqualsAndHashCode
@Embeddable
public class Endereco implements Serializable {

	private static final long serialVersionUID = -1215304834350865024L;
	@Column(length = 30)
	private String cep;
	private String logradouro;
	@Column(length = 30)
	private String numero;
	@Column(length = 50)
	private String complemento;
	private String bairro;
	private String municipio;
	@Enumerated(EnumType.STRING)
	@Column(length = 2)
	private UF uf;

}
