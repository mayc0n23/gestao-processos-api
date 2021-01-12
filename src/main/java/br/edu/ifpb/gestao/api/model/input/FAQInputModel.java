package br.edu.ifpb.gestao.api.model.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FAQInputModel {
	
	@NotBlank
	private String pergunta;
	
}