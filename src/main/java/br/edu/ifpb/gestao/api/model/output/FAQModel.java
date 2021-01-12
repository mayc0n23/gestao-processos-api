package br.edu.ifpb.gestao.api.model.output;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FAQModel {
	
	private Long id;
	
	private String pergunta;
	
	private String resposta;
	
	private boolean ativa;
	
}