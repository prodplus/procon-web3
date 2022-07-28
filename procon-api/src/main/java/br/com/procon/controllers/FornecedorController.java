package br.com.procon.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.procon.models.Fornecedor;
import br.com.procon.models.dtos.FornecedorDto;
import br.com.procon.services.FornecedorService;

/**
 * 
 * @author Marlon F. Garcia
 *
 */
@RestController
@RequestMapping("/fornecedores")
@CrossOrigin("http://localhost:4200")
public class FornecedorController {

	@Autowired
	private FornecedorService fornecedorService;

	@PostMapping
	public ResponseEntity<Fornecedor> salvar(@RequestBody Fornecedor fornecedor) {
		return ResponseEntity.ok(this.fornecedorService.salvar(fornecedor));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Fornecedor> atualizar(@PathVariable Integer id,
			@RequestBody Fornecedor fornecedor) {
		return ResponseEntity.ok(this.fornecedorService.atualizar(id, fornecedor));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Fornecedor> buscar(@PathVariable Integer id) {
		return ResponseEntity.ok(this.fornecedorService.buscar(id));
	}

	@GetMapping("/listar/{pagina}/{quant}")
	public ResponseEntity<Page<FornecedorDto>> listar(@PathVariable int pagina,
			@PathVariable int quant) {
		return ResponseEntity.ok(this.fornecedorService.listar(pagina, quant));
	}

	@GetMapping("/listar/{pagina}/{quant}/{parametro}")
	public ResponseEntity<Page<FornecedorDto>> listar(@PathVariable int pagina,
			@PathVariable int quant, @PathVariable String parametro) {
		return ResponseEntity.ok(this.fornecedorService.listar(pagina, quant, parametro));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Integer id) {
		this.fornecedorService.excluir(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/existe/{fantasia}")
	public ResponseEntity<Boolean> fornecedorExiste(@PathVariable String fantasia) {
		return ResponseEntity.ok(this.fornecedorService.fornecedorExiste(fantasia));
	}

	@GetMapping("/cnpj/{cnpj}")
	public ResponseEntity<Boolean> cnpjExiste(@PathVariable String cnpj) {
		return ResponseEntity.ok(this.fornecedorService.cnpjExiste(cnpj));
	}

}
