package br.com.procon.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.procon.models.Atendimento;
import br.com.procon.models.Consumidor;
import br.com.procon.models.Fornecedor;
import br.com.procon.models.dtos.AtendimentoDto;
import br.com.procon.models.dtos.ConsumidorDto;
import br.com.procon.models.dtos.FornecedorDto;

/**
 * 
 * @author Marlon F. Garcia
 *
 */
public class TransformaDtos {

	/**
	 * Transforma uma página de Atendimentos em uma página de atendimentos Dtos.
	 * 
	 * @param lista
	 * @param page
	 * @param pageable
	 * @return
	 */
	public static Page<AtendimentoDto> transformaDtos(List<AtendimentoDto> lista,
			Page<Atendimento> page, Pageable pageable) {
		page.getContent().forEach(a -> {
			List<ConsumidorDto> consumidores = new ArrayList<>();
			List<FornecedorDto> fornecedores = new ArrayList<>();
			a.getConsumidores().forEach(c -> consumidores.add(new ConsumidorDto(c.getId(),
					c.getTipo().name(), c.getDenominacao(), c.getCadastro())));
			a.getFornecedores().forEach(f -> fornecedores.add(new FornecedorDto(f.getId(),
					f.getFantasia(), f.getRazaoSocial(), f.getCnpj())));
			lista.add(new AtendimentoDto(a.getId(), consumidores, fornecedores, a.getData(),
					a.getAtendente().getId()));
		});
		return new PageImpl<>(lista, pageable, page.getTotalElements());
	}

	/**
	 * Transforma uma lista de Atendimentos em uma lista de AtendimentosDto.
	 * 
	 * @param lista
	 * @return
	 */
	public static List<AtendimentoDto> transformaAtenDtos(List<Atendimento> lista) {
		List<AtendimentoDto> atendimentos = new ArrayList<>();
		lista.forEach(a -> {
			atendimentos.add(new AtendimentoDto(a.getId(), transformaConsDtos(a.getConsumidores()),
					transformaFornDtos(a.getFornecedores()), a.getData(),
					a.getAtendente().getId()));
		});
		return atendimentos;
	}

	/**
	 * Transforma lista de Consumidores em uma lista de ConsumidoresDto
	 * 
	 * @param consumidores
	 * @return
	 */
	public static List<ConsumidorDto> transformaConsDtos(List<Consumidor> consumidores) {
		List<ConsumidorDto> lista = new ArrayList<>();
		consumidores.forEach(c -> lista.add(new ConsumidorDto(c.getId(), c.getTipo().name(),
				c.getDenominacao(), c.getCadastro())));
		return lista;
	}

	/**
	 * Transforma uma lista de Fornecedores em uma lista de FornecedorDto.
	 * 
	 * @param fornecedores
	 * @return
	 */
	public static List<FornecedorDto> transformaFornDtos(List<Fornecedor> fornecedores) {
		List<FornecedorDto> lista = new ArrayList<>();
		fornecedores.forEach(f -> lista.add(
				new FornecedorDto(f.getId(), f.getFantasia(), f.getRazaoSocial(), f.getCnpj())));
		return lista;
	}

}
