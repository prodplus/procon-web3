package br.com.procon.models.forms;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import br.com.procon.models.Consumidor;
import br.com.procon.models.Fornecedor;
import br.com.procon.models.Processo;
import br.com.procon.models.enums.TipoProcesso;
import br.com.procon.services.ConsumidorService;
import br.com.procon.services.FornecedorService;
import br.com.procon.services.UsuarioService;
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
public class ProcessoForm implements Serializable {

	private static final long serialVersionUID = 208626838808877043L;

	private Integer id;
	@NotNull(message = "o tipo é obrigatório!")
	private TipoProcesso tipo;
	@Pattern(regexp = "^[0-9]{3,4}\\/20[0-9]{2}", message = "autos inválidos!")
	private String autos;
	@NotNull(message = "a data é obrigatória!")
	private LocalDate data;
	private List<Integer> consumidores = new ArrayList<>();
	private List<Integer> representantes = new ArrayList<>();
	@Size(min = 1, message = "deve haver pelo menos um fornecedor!")
	private List<Integer> fornecedores = new ArrayList<>();
	private String relato;
	private Integer atendente;

	public Processo converter(ConsumidorService consumidorService,
			FornecedorService fornecedorService, UsuarioService usuarioService) {
		List<Consumidor> consI = new ArrayList<>();
		List<Fornecedor> fornI = new ArrayList<>();
		List<Consumidor> reprI = new ArrayList<>();
		this.consumidores.forEach(c -> consI.add(consumidorService.buscar(c)));
		this.representantes.forEach(r -> reprI.add(consumidorService.buscar(r)));
		this.fornecedores.forEach(f -> fornI.add(fornecedorService.buscar(f)));
		return new Processo(this.getId(), this.getTipo(), this.getAutos(), this.getData(), consI,
				reprI, fornI, new ArrayList<>(), this.getRelato(), null,
				usuarioService.buscarI(this.getAtendente()));
	}

}
