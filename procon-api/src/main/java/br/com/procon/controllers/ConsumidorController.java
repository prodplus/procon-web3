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

import br.com.procon.models.Consumidor;
import br.com.procon.models.dtos.ConsumidorDto;
import br.com.procon.services.ConsumidorService;

/**
 * 
 * @author Marlon F. Garcia
 *
 */
@RestController
@RequestMapping("/consumidores")
@CrossOrigin("http://localhost:4200")
public class ConsumidorController {

	@Autowired
	private ConsumidorService consumidorService;

	@PostMapping
	public ResponseEntity<Consumidor> salvar(@RequestBody Consumidor consumidor) {
		return ResponseEntity.ok(this.consumidorService.salvar(consumidor));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Consumidor> atualizar(@PathVariable Integer id,
			@RequestBody Consumidor consumidor) {
		return ResponseEntity.ok(this.consumidorService.atualizar(id, consumidor));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Consumidor> buscar(@PathVariable Integer id) {
		return ResponseEntity.ok(this.consumidorService.buscar(id));
	}

	@GetMapping("/listar/{pagina}/{quant}")
	public ResponseEntity<Page<ConsumidorDto>> listar(@PathVariable int pagina,
			@PathVariable int quant) {
		return ResponseEntity.ok(this.consumidorService.listar(pagina, quant));
	}

	@GetMapping("/listar/{pagina}/{quant}/{parametro}")
	public ResponseEntity<Page<ConsumidorDto>> listar(@PathVariable int pagina,
			@PathVariable int quant, @PathVariable String parametro) {
		return ResponseEntity.ok(this.consumidorService.listar(pagina, quant, parametro));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Integer id) {
		this.consumidorService.excluir(id);
		return ResponseEntity.ok().build();
	}

}
