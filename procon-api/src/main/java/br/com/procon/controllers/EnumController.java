package br.com.procon.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.procon.models.enums.TipoPessoa;
import br.com.procon.models.enums.TipoProcesso;
import br.com.procon.models.enums.UF;

/**
 * 
 * @author Marlon F. Garcia
 *
 */
@RestController
@RequestMapping("/enums")
@CrossOrigin("http://localhost:4200")
public class EnumController {
	
	@GetMapping("/tipos_pessoa")
	public ResponseEntity<TipoPessoa[]> getTipoPessoa() {
		return ResponseEntity.ok(TipoPessoa.values());
	}
	
	@GetMapping("/ufs")
	public ResponseEntity<UF[]> getUfs() {
		return ResponseEntity.ok(UF.values());
	}
	
	@GetMapping("/tipos_processo")
	public ResponseEntity<TipoProcesso[]> getTiposProcesso() {
		return ResponseEntity.ok(TipoProcesso.values());
	}

}
