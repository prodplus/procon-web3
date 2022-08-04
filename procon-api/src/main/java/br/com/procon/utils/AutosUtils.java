package br.com.procon.utils;

import java.util.Collections;
import java.util.List;

import br.com.procon.models.dtos.ProcessoDto;

/**
 * 
 * @author Marlon F. Garcia
 *
 */
public class AutosUtils {

	/**
	 * Obtém um valor numérico da string autos para ordenação dos processos.
	 * 
	 * @param autos
	 * @return
	 */
	public static int getNroAutos(String autos) {
		String[] parts = autos.split("/");
		int nro = Integer.parseInt(parts[1]) * 100;
		nro += Integer.parseInt(parts[0]);
		return nro;
	}

	/**
	 * Gera uma string como o formato de Autos.
	 * 
	 * @param processosAno
	 * @param ano
	 * @return
	 */
	public static String getAutos(List<ProcessoDto> processosAno, Integer ano) {
		if (!processosAno.isEmpty()) {
			Collections.sort(processosAno);
			int nro = Integer.parseInt(processosAno.get(0).autos().split("/")[0]) + 1;
			return String.format("%03d/%04d", nro, ano);
		} else {
			return String.format("%03d/%04d", 1, ano);
		}
	}

}
