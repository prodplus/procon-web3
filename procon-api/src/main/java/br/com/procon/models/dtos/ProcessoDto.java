package br.com.procon.models.dtos;

import java.time.LocalDate;
import java.util.List;

import br.com.procon.models.enums.Situacao;
import br.com.procon.models.enums.TipoProcesso;
import br.com.procon.utils.AutosUtils;

/**
 * 
 * @author Marlon F. Garcia
 *
 */
public record ProcessoDto(Integer id, String autos, TipoProcesso tipo, LocalDate data,
		List<ConsumidorDto> consumidores, List<ConsumidorDto> representantes,
		List<FornecedorDto> fornecedores, Situacao situacao, UsuarioDto atendente, int ordem)
		implements Comparable<ProcessoDto> {

	@Override
	public int compareTo(ProcessoDto o) {
		if (this.autos != null && o.autos != null) {
			return (AutosUtils.getNroAutos(this.autos) - AutosUtils.getNroAutos(o.autos)) * -1;
		} else {
			return 0;
		}
	}

}
