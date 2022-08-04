package br.com.procon.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.procon.models.enums.TipoLog;
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
@Entity
public class Log implements Serializable, Comparable<Log> {

	private static final long serialVersionUID = 7174716353137842353L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false)
	private LocalDateTime data;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Usuario usuario;
	@Column(nullable = false)
	private String log;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private TipoLog tipo;

	@Override
	public int compareTo(Log o) {
		if (this.getData() != null && o.getData() != null)
			return this.getData().compareTo(o.getData());
		return 0;
	}

}
