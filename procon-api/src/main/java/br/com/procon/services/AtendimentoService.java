package br.com.procon.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.procon.models.Atendimento;
import br.com.procon.models.auxiliares.FornecedorNro;
import br.com.procon.models.dtos.AtendimentoDto;
import br.com.procon.models.dtos.FornecedorDto;
import br.com.procon.models.enums.TipoLog;
import br.com.procon.models.forms.AtendimentoForm;
import br.com.procon.repositories.AtendimentoRepository;
import br.com.procon.utils.TransformaDtos;

/**
 * 
 * @author Marlon F. Garcia
 *
 */
@Service
public class AtendimentoService {

	@Autowired
	private AtendimentoRepository atendimentoRepository;
	@Autowired
	private ConsumidorService consumidorService;
	@Autowired
	private FornecedorService fornecedorService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private LogService logService;

	public AtendimentoDto salvar(@Valid AtendimentoForm atendimento) {
		try {
			Atendimento ate = this.atendimentoRepository.save(atendimento.converter(
					this.consumidorService, this.fornecedorService, this.usuarioService));
			this.logService.salvar(LocalDateTime.now(), "Atendimento " + ate.getId(),
					TipoLog.INSERCAO);
			return new AtendimentoDto(ate.getId(),
					TransformaDtos.transformaConsDtos(ate.getConsumidores()),
					TransformaDtos.transformaFornDtos(ate.getFornecedores()), ate.getData(),
					ate.getAtendente().getId());
		} catch (ValidationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "erro de validação!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public AtendimentoDto atualizar(Integer id, @Valid AtendimentoForm atendimento) {
		try {
			return this.atendimentoRepository.findById(id).map(a -> {
				a = atendimento.converter(this.consumidorService, this.fornecedorService,
						this.usuarioService);
				this.logService.salvar(LocalDateTime.now(), "Atendimento " + a.getId(),
						TipoLog.ATUALIZACAO);
				Atendimento ate = this.atendimentoRepository.save(a);
				return new AtendimentoDto(ate.getId(),
						TransformaDtos.transformaConsDtos(ate.getConsumidores()),
						TransformaDtos.transformaFornDtos(ate.getFornecedores()), ate.getData(),
						ate.getAtendente().getId());
			}).orElseThrow(() -> new EntityNotFoundException());
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "atendimento não localizado!",
					e.getCause());
		} catch (ValidationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "erro de validação!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public AtendimentoDto buscar(Integer id) {
		try {
			Atendimento ate = this.atendimentoRepository.findById(id)
					.orElseThrow(() -> new EntityNotFoundException());
			return new AtendimentoDto(ate.getId(),
					TransformaDtos.transformaConsDtos(ate.getConsumidores()),
					TransformaDtos.transformaFornDtos(ate.getFornecedores()), ate.getData(),
					ate.getAtendente().getId());
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "atendimento não localizado!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Page<AtendimentoDto> listar(int pagina, int quant) {
		try {
			Pageable pageable = PageRequest.of(pagina, quant, Direction.DESC, "id");
			List<AtendimentoDto> lista = new ArrayList<>();
			Page<Atendimento> page = this.atendimentoRepository.findAll(pageable);
			return TransformaDtos.transformaDtos(lista, page, pageable);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Page<AtendimentoDto> listarConsumidor(int pagina, int quant, String parametro) {
		try {
			Pageable pageable = PageRequest.of(pagina, quant, Direction.DESC, "id");
			List<AtendimentoDto> lista = new ArrayList<>();
			Page<Atendimento> page = this.atendimentoRepository
					.findAllByConsumidoresDenominacaoContainingIgnoreCaseOrConsumidoresCadastroContaining(
							parametro, parametro, pageable);
			return TransformaDtos.transformaDtos(lista, page, pageable);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Page<AtendimentoDto> listarFornecedor(int pagina, int quant, String parametro) {
		try {
			Pageable pageable = PageRequest.of(pagina, quant, Direction.DESC, "id");
			List<AtendimentoDto> lista = new ArrayList<>();
			Page<Atendimento> page = this.atendimentoRepository
					.findAllByFornecedoresFantasiaContainingIgnoreCaseOrFornecedoresRazaoSocialContainingIgnoreCase(
							parametro, parametro, pageable);
			return TransformaDtos.transformaDtos(lista, page, pageable);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public void excluir(Integer id) {
		try {
			this.atendimentoRepository.findById(id).map(a -> {
				return this.logService.salvar(LocalDateTime.now(), "Atendimento: " + a.getId(),
						TipoLog.EXCLUSAO);
			}).orElseThrow(() -> new EntityNotFoundException());
			this.atendimentoRepository.deleteById(id);
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "atendimento não localizado!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Long atendimentosAno() {
		try {
			return this.atendimentoRepository
					.countByDataAfter(LocalDate.of(LocalDate.now().getYear(), 1, 1));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<FornecedorNro> ranking(Integer ano) {
		try {
			List<FornecedorNro> fornecedores = new ArrayList<>();
			List<Atendimento> ateAno = this.atendimentoRepository
					.findAllByDataBetween(LocalDate.of(ano, 1, 1), LocalDate.of(ano + 1, 1, 1));
			ateAno.forEach(a -> {
				a.getFornecedores().forEach(f -> {
					FornecedorNro teste = new FornecedorNro(new FornecedorDto(f.getId(),
							f.getFantasia(), f.getRazaoSocial(), f.getCnpj()), 1);
					if (!fornecedores.contains(teste))
						fornecedores.add(teste);
					else {
						int index = fornecedores.indexOf(teste);
						Integer quant = fornecedores.get(index).getProcessos();
						fornecedores.get(index).setProcessos(quant + 1);
					}
				});
			});
			return fornecedores;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

}
