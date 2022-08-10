package br.com.procon.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.procon.models.auxiliares.FornecedorNro;
import br.com.procon.models.auxiliares.Movimento;
import br.com.procon.models.auxiliares.ProcDesc;
import br.com.procon.models.auxiliares.RelatoId;
import br.com.procon.models.dtos.LogDto;
import br.com.procon.models.dtos.ProcessoDto;
import br.com.procon.models.enums.Situacao;
import br.com.procon.models.forms.ProcessoForm;
import br.com.procon.services.ProcessoService;

/**
 * 
 * @author Marlon F. Garcia
 *
 */
@RestController
@RequestMapping("/processos")
public class ProcessoController {

	@Autowired
	private ProcessoService processoService;

	@PostMapping
	public ResponseEntity<ProcessoDto> salvar(@RequestBody ProcessoForm processo) {
		return ResponseEntity.ok(this.processoService.salvar(processo));
	}

	@PutMapping("{id}")
	public ResponseEntity<ProcessoDto> atualizar(@PathVariable Integer id,
			@RequestBody ProcessoForm processo) {
		return ResponseEntity.ok(this.processoService.atualizar(id, processo));
	}

	@GetMapping("/relato/{id}")
	public ResponseEntity<RelatoId> getRelato(@PathVariable Integer id) {
		return ResponseEntity.ok(this.processoService.getRelato(id));
	}

	@PutMapping("/movimentos/{id}")
	public ResponseEntity<List<Movimento>> setMovimentacao(@PathVariable Integer id,
			@RequestBody List<Movimento> movimentacao) {
		return ResponseEntity.ok(this.processoService.setMovimentacao(id, movimentacao));
	}

	@GetMapping("/movimentos/{id}")
	public ResponseEntity<List<Movimento>> getMovimentacao(@PathVariable Integer id) {
		return ResponseEntity.ok(this.processoService.getMovimentacao(id));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProcessoDto> buscar(@PathVariable Integer id) {
		return ResponseEntity.ok(this.processoService.buscar(id));
	}

	@GetMapping("/listar/{pagina}/{quant}")
	public ResponseEntity<Page<ProcessoDto>> listar(@PathVariable int pagina,
			@PathVariable int quant) {
		return ResponseEntity.ok(this.processoService.listar(pagina, quant));
	}

	@GetMapping("/por_autos/{pagina}/{quant}/{autos}")
	public ResponseEntity<Page<ProcessoDto>> listarPorAutos(@PathVariable int pagina,
			@PathVariable int quant, @PathVariable String autos) {
		return ResponseEntity.ok(this.processoService.listarPorAutos(pagina, quant, autos));
	}

	@GetMapping("/por_consumidor/{pagina}/{quant}/{parametro}")
	public ResponseEntity<Page<ProcessoDto>> listarPorConsumidor(@PathVariable int pagina,
			@PathVariable int quant, @PathVariable String parametro) {
		return ResponseEntity
				.ok(this.processoService.listarPorConsumidor(pagina, quant, parametro));
	}

	@GetMapping("/por_consumidor/{idConsumidor}")
	public ResponseEntity<List<ProcessoDto>> listarPorConsumidor(
			@PathVariable Integer idConsumidor) {
		return ResponseEntity.ok(this.processoService.listarPorConsumidor(idConsumidor));
	}

	@GetMapping("/por_fornecedor/{pagina}/{quant}/{parametro}")
	public ResponseEntity<Page<ProcessoDto>> listarPorFornecedor(@PathVariable int pagina,
			@PathVariable int quant, @PathVariable String parametro) {
		return ResponseEntity
				.ok(this.processoService.listarPorFornecedor(pagina, quant, parametro));
	}

	@GetMapping("/por_fornecedor_i/{pagina}/{quant}/{idFornecedor}")
	public ResponseEntity<Page<ProcessoDto>> listarPorFornecedor(@PathVariable int pagina,
			@PathVariable int quant, @PathVariable Integer idFornecedor) {
		return ResponseEntity
				.ok(this.processoService.listarPorFornecedor(pagina, quant, idFornecedor));
	}

	@GetMapping("/por_situacao/{pagina}/{quant}/{situacao}")
	public ResponseEntity<Page<ProcessoDto>> listarPorSituacao(@PathVariable int pagina,
			@PathVariable int quant, @PathVariable Situacao situacao) {
		return ResponseEntity.ok(this.processoService.listarPorSituacao(pagina, quant, situacao));
	}

	@GetMapping("/por_fornecedor_situacao/{idFornecedor}/{situacao}")
	public ResponseEntity<List<ProcessoDto>> listarPorFornecedorSituacao(
			@PathVariable Integer idFornecedor, @PathVariable Situacao situacao) {
		return ResponseEntity
				.ok(this.processoService.listarPorFornecedorSituacao(idFornecedor, situacao));
	}

	@GetMapping("/por_fornecedor_desc/{idFornecedor}/{situacao}")
	public ResponseEntity<List<ProcDesc>> listarPorFornecedorSituacaoDesc(
			@PathVariable Integer idFornecedor, @PathVariable Situacao situacao) {
		return ResponseEntity
				.ok(this.processoService.listarPorFornecedorSituacaoDesc(idFornecedor, situacao));
	}

	@GetMapping("/por_situacao/{situacao}")
	public ResponseEntity<List<ProcessoDto>> listarPorSituacao(@PathVariable Situacao situacao) {
		return ResponseEntity.ok(this.processoService.listarPorSituacao(situacao));
	}

	@GetMapping("/por_situacao/{situacao}/{inicio}/{fim}")
	public ResponseEntity<List<ProcessoDto>> listarPorSituacao(@PathVariable Situacao situacao,
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
		return ResponseEntity.ok(this.processoService.listarPorSituacao(situacao, inicio, fim));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Integer id) {
		this.processoService.excluir(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/movimentacao_dia/{dia}")
	public ResponseEntity<List<LogDto>> movimentacaoDia(
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dia) {
		return ResponseEntity.ok(this.processoService.movimentacaoDia(dia));
	}

	@GetMapping("/ranking/{ano}")
	public ResponseEntity<List<FornecedorNro>> ranking(@PathVariable Integer ano) {
		return ResponseEntity.ok(this.processoService.ranking(ano));
	}

}
