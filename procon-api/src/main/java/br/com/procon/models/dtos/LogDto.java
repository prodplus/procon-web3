package br.com.procon.models.dtos;

import java.time.LocalDateTime;

import br.com.procon.models.enums.TipoLog;

/**
 * 
 * @author Marlon F. Garcia
 *
 */
public record LogDto(LocalDateTime data, UsuarioDto usuario, String log, TipoLog tipo) {

}
