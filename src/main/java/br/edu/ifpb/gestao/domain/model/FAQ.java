package br.edu.ifpb.gestao.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
public class FAQ {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String pergunta;
	
	@Column(nullable = false)
	private String resposta;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Processo processo;
	
	@Column(nullable = false)
	private boolean ativa;
	
}