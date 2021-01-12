package br.edu.ifpb.gestao.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Protocolo {
	
	@Column(name = "protocolo_email")
	private String email;
	
	@Column(name = "protocolo_telefone")
	private String telefone;
}