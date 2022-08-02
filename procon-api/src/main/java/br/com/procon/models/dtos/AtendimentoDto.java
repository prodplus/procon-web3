package br.com.procon.models.dtos;

import java.time.LocalDate;
import java.util.List;

/**
 * 
 * @author Marlon F. Garcia
 *
 */
public record AtendimentoDto(Integer id, List<ConsumidorDto> consumidores,
		List<FornecedorDto> fornecedores, LocalDate data, Integer atendente) {

}
