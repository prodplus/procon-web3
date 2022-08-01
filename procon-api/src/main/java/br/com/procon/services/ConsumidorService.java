package br.com.procon.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.procon.models.Consumidor;
import br.com.procon.models.dtos.ConsumidorDto;
import br.com.procon.models.enums.TipoLog;
import br.com.procon.repositories.ConsumidorRepository;

/**
 * 
 * @author Marlon F. Garcia
 *
 */
@Service
public class ConsumidorService {

	@Autowired
	private ConsumidorRepository consumidorRepository;
	@Autowired
	private LogService logService;

	public Consumidor salvar(@Valid Consumidor consumidor) {
		try {
			Consumidor cons = this.consumidorRepository.save(consumidor);
			this.logService.salvar(LocalDateTime.now(), "Consumidor " + cons.getDenominacao(),
					TipoLog.INSERCAO);
			return cons;
		} catch (ValidationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "erro de validação!",
					e.getCause());
		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "consumidor já cadastrado!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Consumidor atualizar(Integer id, @Valid Consumidor consumidor) {
		try {
			return this.consumidorRepository.findById(id).map(c -> {
				c.setCadastro(consumidor.getCadastro());
				c.setDenominacao(consumidor.getDenominacao());
				c.setEmail(consumidor.getEmail());
				c.setEndereco(consumidor.getEndereco());
				c.setFones(consumidor.getFones());
				c.setNascimento(consumidor.getNascimento());
				c.setTipo(consumidor.getTipo());
				Consumidor cons = this.consumidorRepository.save(c);
				this.logService.salvar(LocalDateTime.now(), "Consumidor " + cons.getDenominacao(),
						TipoLog.ATUALIZACAO);
				return cons;
			}).orElseThrow(() -> new EntityNotFoundException());
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "consumidor não localizado!",
					e.getCause());
		} catch (ValidationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "erro de validação!",
					e.getCause());
		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "consumidor já cadastrado!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Consumidor buscar(Integer id) {
		try {
			return this.consumidorRepository.findById(id)
					.orElseThrow(() -> new EntityNotFoundException());
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "consumidor não localizado!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Page<ConsumidorDto> listar(int pagina, int quant) {
		try {
			Pageable pageable = PageRequest.of(pagina, quant, Direction.ASC, "denominacao");
			Page<Consumidor> page = this.consumidorRepository.findAll(pageable);
			List<ConsumidorDto> lista = new ArrayList<>();
			return transformaDto(lista, page, pageable);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Page<ConsumidorDto> listar(int pagina, int quant, String parametro) {
		try {
			Pageable pageable = PageRequest.of(pagina, quant, Direction.ASC, "denominacao");
			List<ConsumidorDto> lista = new ArrayList<>();
			if (Character.isDigit(parametro.charAt(0))) {
				Page<Consumidor> page = this.consumidorRepository
						.findAllByCadastroContaining(parametro, pageable);
				return transformaDto(lista, page, pageable);
			} else {
				Page<Consumidor> page = this.consumidorRepository
						.findAllByDenominacaoContaining(parametro, pageable);
				return transformaDto(lista, page, pageable);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public void excluir(Integer id) {
		try {
			this.consumidorRepository.findById(id)
					.map(c -> this.logService.salvar(LocalDateTime.now(),
							"Consumidor " + c.getDenominacao(), TipoLog.EXCLUSAO))
					.orElseThrow(() -> new EntityNotFoundException());
			this.consumidorRepository.deleteById(id);
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "consumidor não localizado!",
					e.getCause());
		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					"não é possível excluir o consumidor!", e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public boolean consumidorExiste(String cadastro) {
		try {
			return this.consumidorRepository.existsByCadastro(cadastro);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	private static Page<ConsumidorDto> transformaDto(List<ConsumidorDto> lista,
			Page<Consumidor> page, Pageable pageable) {
		page.getContent().forEach(c -> lista.add(new ConsumidorDto(c.getId(), c.getTipo().name(),
				c.getDenominacao(), c.getCadastro())));
		return new PageImpl<>(lista, pageable, page.getTotalElements());
	}

}
