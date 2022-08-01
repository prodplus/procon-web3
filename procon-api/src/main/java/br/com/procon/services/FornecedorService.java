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

import br.com.procon.models.Fornecedor;
import br.com.procon.models.dtos.FornecedorDto;
import br.com.procon.models.enums.TipoLog;
import br.com.procon.repositories.FornecedorRepository;

/**
 * 
 * @author Marlon F. Garcia
 *
 */
@Service
public class FornecedorService {

	@Autowired
	private FornecedorRepository fornecedorRepository;
	@Autowired
	private LogService logService;

	public Fornecedor salvar(@Valid Fornecedor fornecedor) {
		try {
			Fornecedor forn = this.fornecedorRepository.save(fornecedor);
			this.logService.salvar(LocalDateTime.now(), "Fornecedor " + forn.getFantasia(),
					TipoLog.INSERCAO);
			return forn;
		} catch (ValidationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "erro de validação!",
					e.getCause());
		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "fornecedor já cadastrado!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Fornecedor atualizar(Integer id, @Valid Fornecedor fornecedor) {
		try {
			return this.fornecedorRepository.findById(id).map(f -> {
				f.setCnpj(fornecedor.getCnpj());
				f.setEmail(fornecedor.getEmail());
				f.setEndereco(fornecedor.getEndereco());
				f.setFantasia(fornecedor.getFantasia());
				f.setFones(fornecedor.getFones());
				f.setRazaoSocial(fornecedor.getRazaoSocial());
				this.logService.salvar(LocalDateTime.now(), "Fornecedor " + f.getFantasia(),
						TipoLog.ATUALIZACAO);
				return this.fornecedorRepository.save(f);
			}).orElseThrow(() -> new EntityNotFoundException());
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "fornecedor não localizado!",
					e.getCause());
		} catch (ValidationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "erro de validação!",
					e.getCause());
		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "fornecedor já cadastrado!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Fornecedor buscar(Integer id) {
		try {
			return this.fornecedorRepository.findById(id)
					.orElseThrow(() -> new EntityNotFoundException());
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "fornecedor não localizado!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Page<FornecedorDto> listar(int pagina, int quant) {
		try {
			Pageable pageable = PageRequest.of(pagina, quant, Direction.ASC, "fantasia");
			Page<Fornecedor> page = this.fornecedorRepository.findAll(pageable);
			List<FornecedorDto> lista = new ArrayList<>();
			return transformaDto(lista, page, pageable);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Page<FornecedorDto> listar(int pagina, int quant, String parametro) {
		try {
			Pageable pageable = PageRequest.of(pagina, quant, Direction.ASC, "fantasia");
			List<FornecedorDto> lista = new ArrayList<>();
			if (Character.isDigit(parametro.charAt(0))) {
				Page<Fornecedor> page = this.fornecedorRepository.findAllByCnpjContaining(parametro,
						pageable);
				return transformaDto(lista, page, pageable);
			} else {
				Page<Fornecedor> page = this.fornecedorRepository
						.findAllByFantasiaContainingIgnoreCaseOrRazaoSocialContainingIgnoreCase(
								parametro, parametro, pageable);
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
			this.fornecedorRepository.findById(id)
					.map(f -> this.logService.salvar(LocalDateTime.now(),
							"Fornecedor " + f.getFantasia(), TipoLog.EXCLUSAO))
					.orElseThrow(() -> new EntityNotFoundException());
			this.fornecedorRepository.deleteById(id);
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "fornecedor não localizado!",
					e.getCause());
		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					"não é possível excluir o fornecedor!", e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public boolean fornecedorExiste(String fantasia) {
		try {
			return this.fornecedorRepository.existsByFantasia(fantasia);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public boolean cnpjExiste(String cnpj) {
		try {
			return this.fornecedorRepository.existsByCnpj(cnpj);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	private static Page<FornecedorDto> transformaDto(List<FornecedorDto> lista,
			Page<Fornecedor> page, Pageable pageable) {
		page.getContent().forEach(f -> lista.add(
				new FornecedorDto(f.getId(), f.getFantasia(), f.getRazaoSocial(), f.getCnpj())));
		return new PageImpl<>(lista, pageable, page.getTotalElements());
	}

}
