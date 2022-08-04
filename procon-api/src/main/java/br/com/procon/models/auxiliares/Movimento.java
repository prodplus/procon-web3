package br.com.procon.models.auxiliares;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.procon.models.enums.Situacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author Marlon F. Garcia
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Movimento implements Serializable, Comparable<Movimento> {

	private static final long serialVersionUID = -714242681743161646L;
	@Column(nullable = false)
	private LocalDate data;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private Situacao de;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private Situacao para;
	private String averbacao;
	private LocalDate auxD;
	@JsonFormat(pattern = "HH:mm")
	private LocalTime auxT;

	@Override
	public int compareTo(Movimento o) {
		if (this.getData() != null && o.getData() != null)
			return this.getData().compareTo(o.getData()) * -1;
		return 0;
	}

}
