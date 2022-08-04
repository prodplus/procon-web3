package br.com.procon.models.auxiliares;

import br.com.procon.models.dtos.ProcessoDto;

/**
 * 
 * @author Marlon F. Garcia
 *
 */
public record ProcDesc(ProcessoDto processo, String descricao) implements Comparable<ProcDesc> {

	@Override
	public int compareTo(ProcDesc o) {
		if (descricao != null && o.descricao != null)
			return this.descricao.compareTo(o.descricao);
		return processo.compareTo(o.processo);
	}

}
