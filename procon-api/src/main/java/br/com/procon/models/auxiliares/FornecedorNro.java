package br.com.procon.models.auxiliares;

import java.io.Serializable;

import br.com.procon.models.dtos.FornecedorDto;
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
public class FornecedorNro implements Serializable {

	private static final long serialVersionUID = -5477599842443711857L;
	@EqualsAndHashCode.Include
	private FornecedorDto fornecedor;
	private Integer processos;
	
}
