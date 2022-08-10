package br.com.procon.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.procon.services.RelatorioService;

/**
 * 
 * @author Marlon F. Garcia
 *
 */
@RestController
@RequestMapping("/relatorios")
@CrossOrigin("http://localhost:4200")
public class RelatorioController {

	@Autowired
	private RelatorioService relService;

	@GetMapping(path = "/atendimento/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> atendimentoIni(@PathVariable Integer id) {
		return ResponseEntity.ok()
				.header("Content-Disposition",
						"inline; filename=atendimento_" + id.toString() + ".pdf")
				.contentType(MediaType.APPLICATION_PDF).body(this.relService.atendimentoIni(id));
	}

	@GetMapping(path = "/processo/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> processoIni(@PathVariable Integer id) {
		return ResponseEntity.ok()
				.header("Content-Disposition",
						"inline; filename=processo_" + id.toString() + ".pdf")
				.contentType(MediaType.APPLICATION_PDF).body(this.relService.processoIni(id));
	}

}
