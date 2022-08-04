package br.com.procon.services;

import static br.com.procon.utils.AutosUtils.getAutos;
import static br.com.procon.utils.AutosUtils.getNroAutos;
import static br.com.procon.utils.TransformaDtos.transformaConsDtos;
import static br.com.procon.utils.TransformaDtos.transformaFornDtos;
import static br.com.procon.utils.TransformaDtos.transformaProcDtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.procon.models.Log;
import br.com.procon.models.Processo;
import br.com.procon.models.auxiliares.FornecedorNro;
import br.com.procon.models.auxiliares.Movimento;
import br.com.procon.models.auxiliares.ProcDesc;
import br.com.procon.models.dtos.FornecedorDto;
import br.com.procon.models.dtos.ProcessoDto;
import br.com.procon.models.dtos.UsuarioDto;
import br.com.procon.models.enums.Situacao;
import br.com.procon.models.enums.TipoLog;
import br.com.procon.models.forms.ProcessoForm;
import br.com.procon.repositories.ProcessoRepository;

/**
 * 
 * @author Marlon F. Garcia
 *
 */
@Service
public class ProcessoService {

	@Autowired
	private ProcessoRepository processoRepository;
	@Autowired
	private ConsumidorService consumidorService;
	@Autowired
	private FornecedorService fornecedorService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private LogService logService;

	public ProcessoDto salvar(@Valid ProcessoForm processo) {
		try {
			if (processo.getId() == null) {
				if (processo.getAutos() == null)
					processo.setAutos(getAutos(
							transformaProcDtos(this.processoRepository.findAllByDataBetween(
									LocalDate.of(processo.getData().getYear(), 1, 1),
									LocalDate.of(processo.getData().getYear() + 1, 1, 1))),
							processo.getData().getYear()));
				Processo proc = this.processoRepository.save(processo.converter(
						this.consumidorService, this.fornecedorService, this.usuarioService));
				this.setMovimentacao(proc.getId(), Arrays.asList(new Movimento(proc.getData(),
						Situacao.BALCAO, Situacao.AUTUADO, "", null, null)));
				this.logService.salvar(LocalDateTime.now(), "Processo " + proc.getAutos(),
						TipoLog.INSERCAO);
				return toProcessoDto(proc);
			} else {
				return this.atualizar(processo.getId(), processo);
			}
		} catch (ValidationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "erro de validação!",
					e.getCause());
		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "processo já cadastrado!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public ProcessoDto atualizar(Integer id, @Valid ProcessoForm processo) {
		try {
			return this.processoRepository.findById(id).map(p -> {
				p = processo.converter(this.consumidorService, this.fornecedorService,
						this.usuarioService);
				p.setId(id);
				return toProcessoDto(this.processoRepository.save(p));
			}).orElseThrow(() -> new EntityNotFoundException());
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "processo não localizado!",
					e.getCause());
		} catch (ValidationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "erro de validação!",
					e.getCause());
		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "processo já cadastrado!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<Movimento> setMovimentacao(Integer id, List<Movimento> movimentos) {
		try {
			return this.processoRepository.findById(id).map(p -> {
				p.setMovimentacao(movimentos);
				p = this.processoRepository.save(p);
				return p.getMovimentacao();
			}).orElseThrow(() -> new EntityNotFoundException());
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "processo não localizado!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<Movimento> getMovimentacao(Integer id) {
		try {
			return this.processoRepository.findById(id).map(p -> p.getMovimentacao())
					.orElseThrow(() -> new EntityNotFoundException());
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "processo não localizado!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public ProcessoDto buscar(Integer id) {
		try {
			return this.processoRepository.findById(id).map(p -> toProcessoDto(p))
					.orElseThrow(() -> new EntityNotFoundException());
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "processo não localizado!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Page<ProcessoDto> listar(int pagina, int quant) {
		try {
			Pageable pageable = PageRequest.of(pagina, quant, Direction.ASC, "data");
			Page<Processo> page = this.processoRepository.findAll(pageable);
			return transformaProcDtos(page);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Page<ProcessoDto> listarPorAutos(int pagina, int quant, String autos) {
		try {
			Pageable pageable = PageRequest.of(pagina, quant, Direction.ASC, "data");
			Page<Processo> page = this.processoRepository.findAllByAutosContaining(autos, pageable);
			return transformaProcDtos(page);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Page<ProcessoDto> listarPorConsumidor(int pagina, int quant, String parametro) {
		try {
			Pageable pageable = PageRequest.of(pagina, quant, Direction.ASC, "data");
			Page<Processo> page = this.processoRepository
					.findAllByConsumidoresDenominacaoContainingIgnoreCaseOrConsumidoresCadastroContaining(
							parametro, parametro, pageable);
			return transformaProcDtos(page);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<ProcessoDto> listarPorConsumidor(Integer idConsumidor) {
		try {
			return transformaProcDtos(this.processoRepository.findAllByConsumidoresId(idConsumidor,
					Sort.by(Sort.Direction.DESC, "data")));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Page<ProcessoDto> listarPorFornecedor(int pagina, int quant, String parametro) {
		try {
			Pageable pageable = PageRequest.of(pagina, quant, Direction.ASC, "data");
			Page<Processo> page = this.processoRepository
					.findAllByFornecedoresFantasiaContainingIgnoreCaseOrFornecedoresRazaoSocialContainingIgnoreCaseOrFornecedoresCnpjContaining(
							parametro, parametro, parametro, pageable);
			return transformaProcDtos(page);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Page<ProcessoDto> listarPorFornecedor(int pagina, int quant, Integer idFornecedor) {
		try {
			Pageable pageable = PageRequest.of(pagina, quant, Direction.ASC, "data");
			Page<Processo> page = this.processoRepository.findAllByFornecedoresId(idFornecedor,
					pageable);
			return transformaProcDtos(page);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Page<ProcessoDto> listarPorSituacao(int pagina, int quant, Situacao situacao) {
		try {
			Pageable pageable = PageRequest.of(pagina, quant, Direction.ASC, "data");
			if (situacao.equals(Situacao.EM_ANDAMENTO)) {
				Page<Processo> page = this.processoRepository
						.findAllBySituacaoNotAndSituacaoNotAndSituacaoNot(Situacao.ENCERRADO,
								Situacao.RESOLVIDO, Situacao.NAO_RESOLVIDO, pageable);
				return transformaProcDtos(page);
			} else {
				Page<Processo> page = this.processoRepository.findAllBySituacao(situacao, pageable);
				return transformaProcDtos(page);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<ProcessoDto> listarPorFornecedorSituacao(Integer idFornecedor, Situacao situacao) {
		try {
			List<Processo> processos = this.processoRepository
					.findAllByFornecedoresIdAndSituacao(idFornecedor, situacao);
			return transformaProcDtos(processos);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<ProcDesc> listarPorFornecedorSituacaoDesc(Integer idFornecedor, Situacao situacao) {
		try {
			List<Processo> processos = this.processoRepository
					.findAllByFornecedoresIdAndSituacao(idFornecedor, situacao);
			if (situacao.equals(Situacao.NAO_RESOLVIDO))
				processos = removePrescritos(processos);
			return transformaPositivos(processos);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<ProcessoDto> listarPorSituacao(Situacao situacao) {
		try {
			if (situacao.equals(Situacao.EM_ANDAMENTO))
				return transformaProcDtos(
						this.processoRepository.findAllBySituacaoNotAndSituacaoNotAndSituacaoNot(
								Situacao.RESOLVIDO, Situacao.ENCERRADO, Situacao.NAO_RESOLVIDO));
			else
				return transformaProcDtos(this.processoRepository.findAllBySituacao(situacao));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<Processo> listarPorSituacaoPuro(Situacao situacao) {
		try {
			return this.processoRepository.findAllBySituacao(situacao);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<ProcessoDto> listarPorSituacao(Situacao situacao, LocalDate inicio, LocalDate fim) {
		try {
			if (situacao.equals(Situacao.EM_ANDAMENTO))
				return transformaProcDtos(
						this.processoRepository.findAllByDataBetween(inicio, fim));
			else if (situacao.equals(Situacao.NAO_RESOLVIDO) || situacao.equals(Situacao.ENCERRADO)
					|| situacao.equals(Situacao.RESOLVIDO)) {
				return transformaProcDtos(
						this.processoRepository.findAllBySituacao(situacao).stream()
								.filter(p -> p.getMovimentacao().get(0).getData().isAfter(inicio)
										&& p.getMovimentacao().get(0).getData().isBefore(fim))
								.collect(Collectors.toList()));
			} else
				return transformaProcDtos(this.processoRepository
						.findAllBySituacaoAndDataBetween(situacao, inicio, fim));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public void excluir(Integer id) {
		try {
			Processo proc = this.processoRepository.findById(id)
					.orElseThrow(() -> new EntityNotFoundException());
			this.logService.salvar(LocalDateTime.now(), "Processo " + proc.getAutos(),
					TipoLog.EXCLUSAO);
			this.processoRepository.deleteById(id);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<Log> movimentacaoDia(LocalDate dia) {
		try {
			LocalDateTime inicio = LocalDateTime.of(dia, LocalTime.of(0, 0));
			LocalDateTime fim = LocalDateTime.of(dia, LocalTime.of(23, 59));
			return this.logService.listar(inicio, fim);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<FornecedorNro> ranking(Integer ano) {
		try {
			List<FornecedorNro> fornecedores = new ArrayList<>();
			List<Processo> ateAno = this.processoRepository
					.findAllByDataBetween(LocalDate.of(ano, 1, 1), LocalDate.of(ano + 1, 1, 1));
			ateAno.forEach(p -> {
				p.getFornecedores().forEach(f -> {
					FornecedorNro teste = new FornecedorNro(new FornecedorDto(f.getId(),
							f.getFantasia(), f.getRazaoSocial(), f.getCnpj()), ano);
					if (!fornecedores.contains(teste))
						fornecedores.add(teste);
					else {
						int index = fornecedores.indexOf(teste);
						Integer quant = fornecedores.get(index).getProcessos();
						fornecedores.get(index).setProcessos(quant + 1);
					}
				});
			});
			fornecedores.removeIf(f -> (f.getProcessos() <= 1));
			Collections.sort(fornecedores);
			return fornecedores;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	private static List<Processo> removePrescritos(List<Processo> processos) {
		List<Processo> procs = new ArrayList<>();
		processos.forEach(p -> {
			LocalDate hoje = LocalDate.now().minusYears(5);
			if (p.getMovimentacao().get(0).getData().isAfter(hoje))
				procs.add(p);
		});
		return procs;
	}

	private static List<ProcDesc> transformaPositivos(List<Processo> processos) {
		List<ProcDesc> descs = new ArrayList<>();
		processos.forEach(p -> {
			ProcDesc desc = new ProcDesc(toProcessoDto(p),
					p.getMovimentacao().get(0).getAverbacao());
			descs.add(desc);
		});
		Collections.sort(descs);
		return descs;
	}

	private static ProcessoDto toProcessoDto(Processo proc) {
		return new ProcessoDto(proc.getId(), proc.getAutos(), proc.getData(),
				transformaConsDtos(proc.getConsumidores()),
				transformaConsDtos(proc.getRepresentantes()),
				transformaFornDtos(proc.getFornecedores()), proc.getSituacao(),
				new UsuarioDto(proc.getAtendente().getId(), proc.getAtendente().getNome(),
						proc.getAtendente().getEmail(), proc.getAtendente().getPerfil().getRole(),
						proc.getAtendente().isAtivo()),
				getNroAutos(proc.getAutos()));
	}

}
