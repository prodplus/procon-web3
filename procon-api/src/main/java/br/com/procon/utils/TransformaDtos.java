package br.com.procon.utils;

import static br.com.procon.utils.AutosUtils.getNroAutos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import br.com.procon.models.Atendimento;
import br.com.procon.models.Consumidor;
import br.com.procon.models.Fornecedor;
import br.com.procon.models.Processo;
import br.com.procon.models.dtos.AtendimentoDto;
import br.com.procon.models.dtos.ConsumidorDto;
import br.com.procon.models.dtos.FornecedorDto;
import br.com.procon.models.dtos.ProcessoDto;
import br.com.procon.models.dtos.UsuarioDto;

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
	public static Page<AtendimentoDto> transformaDtos(Page<Atendimento> page) {
		List<AtendimentoDto> lista = new ArrayList<>();
		page.getContent().forEach(a -> {
			lista.add(new AtendimentoDto(a.getId(), transformaConsDtos(a.getConsumidores()),
					transformaFornDtos(a.getFornecedores()), a.getData(),
					a.getAtendente().getId()));
		});
		return new PageImpl<>(lista, page.getPageable(), page.getTotalElements());
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

	/**
	 * 
	 * @param processos
	 * @return
	 */
	public static List<ProcessoDto> transformaProcDtos(List<Processo> processos) {
		List<ProcessoDto> lista = new ArrayList<>();
		processos.forEach(p -> lista.add(new ProcessoDto(p.getId(), p.getAutos(), p.getData(),
				transformaConsDtos(p.getConsumidores()), transformaConsDtos(p.getRepresentantes()),
				transformaFornDtos(p.getFornecedores()), p.getSituacao(),
				new UsuarioDto(p.getAtendente().getId(), p.getAtendente().getNome(),
						p.getAtendente().getEmail(), p.getAtendente().getPerfil().getRole(),
						p.getAtendente().isAtivo()),
				AutosUtils.getNroAutos(p.getAutos()))));
		return lista;
	}

	public static Page<ProcessoDto> transformaProcDtos(Page<Processo> page) {
		List<ProcessoDto> dtos = new ArrayList<>();
		page.getContent().forEach(proc -> {
			UsuarioDto usuario;
			if (proc.getAtendente() != null)
				usuario = new UsuarioDto(proc.getAtendente().getId(), proc.getAtendente().getNome(),
						proc.getAtendente().getEmail(), proc.getAtendente().getPerfil().getRole(),
						proc.getAtendente().isAtivo());
			else
				usuario = null;
			dtos.add(new ProcessoDto(proc.getId(), proc.getAutos(), proc.getData(),
					transformaConsDtos(proc.getConsumidores()),
					transformaConsDtos(proc.getRepresentantes()),
					transformaFornDtos(proc.getFornecedores()), proc.getSituacao(), usuario,
					getNroAutos(proc.getAutos())));
		});
		Collections.sort(dtos);
		return new PageImpl<>(dtos, page.getPageable(), page.getTotalElements());
	}

}
