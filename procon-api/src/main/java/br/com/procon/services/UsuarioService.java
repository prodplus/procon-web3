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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.procon.models.Usuario;
import br.com.procon.models.dtos.UsuarioDto;
import br.com.procon.models.enums.TipoLog;
import br.com.procon.models.forms.UsuarioForm;
import br.com.procon.repositories.PerfilRepository;
import br.com.procon.repositories.UsuarioRepository;

/**
 * 
 * @author Marlon F. Garcia
 *
 */
@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private PerfilRepository perfilRepository;
	@Autowired
	private LogService logService;

	public UsuarioDto salvar(@Valid UsuarioForm usuario) {
		try {
			Usuario usu = this.usuarioRepository.save(usuario.conveter(this.usuarioRepository,
					this.perfilRepository, new BCryptPasswordEncoder()));
			this.logService.salvar(LocalDateTime.now(), "Usuário " + usu.getEmail(),
					TipoLog.INSERCAO);
			return new UsuarioDto(usu.getId(), usu.getNome(), usu.getEmail(),
					usu.getPerfil().getRole(), true);
		} catch (ValidationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "erro de validação!",
					e.getCause());
		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "usuário já cadastrado!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public UsuarioDto atualizar(Integer id, @Valid UsuarioForm usuario) {
		try {
			return this.usuarioRepository.findById(id).map(u -> {
				u = usuario.conveter(this.usuarioRepository, this.perfilRepository,
						new BCryptPasswordEncoder());
				this.logService.salvar(LocalDateTime.now(), "Usuário " + u.getEmail(),
						TipoLog.ATUALIZACAO);
				return new UsuarioDto(u.getId(), u.getNome(), u.getEmail(), u.getPerfil().getRole(),
						u.isAtivo());
			}).orElseThrow(() -> new EntityNotFoundException());
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "usuário não localizado!",
					e.getCause());
		} catch (ValidationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "erro de validação!",
					e.getCause());
		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "usuário já cadastrado!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public UsuarioDto buscar(Integer id) {
		try {
			Usuario usu = this.usuarioRepository.findById(id)
					.orElseThrow(() -> new EntityNotFoundException());
			return new UsuarioDto(usu.getId(), usu.getNome(), usu.getEmail(),
					usu.getPerfil().getRole(), usu.isAtivo());
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "usuário não localizado!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Usuario buscarI(Integer id) {
		try {
			return this.usuarioRepository.findById(id)
					.orElseThrow(() -> new EntityNotFoundException());
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "usuário não localizado!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Page<UsuarioDto> listar(int pagina, int quant, boolean ativos) {
		try {
			Pageable pageable = PageRequest.of(pagina, quant, Direction.ASC, "nome");
			Page<Usuario> page = this.usuarioRepository.findAllByAtivo(ativos, pageable);
			List<UsuarioDto> lista = new ArrayList<>();
			return transformaDto(lista, page, pageable);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public void excluir(Integer id) {
		try {
			this.usuarioRepository
					.findById(id).map(u -> this.logService.salvar(LocalDateTime.now(),
							"Usuário " + u.getNome(), TipoLog.EXCLUSAO))
					.orElseThrow(() -> new EntityNotFoundException());
			this.usuarioRepository.deleteById(id);
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "usuário não localizado!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	private static Page<UsuarioDto> transformaDto(List<UsuarioDto> lista, Page<Usuario> page,
			Pageable pageable) {
		page.getContent().forEach(u -> lista.add(new UsuarioDto(u.getId(), u.getNome(),
				u.getEmail(), u.getPerfil().getRole(), u.isAtivo())));
		return new PageImpl<>(lista, pageable, page.getTotalElements());
	}

}
