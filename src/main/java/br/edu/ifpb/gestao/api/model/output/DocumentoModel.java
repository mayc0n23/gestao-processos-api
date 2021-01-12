package br.edu.ifpb.gestao.api.model.output;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DocumentoModel {
	
	private Long id;
	
	private String nomeArquivo;
	
	private String contentType;
	
	private String url;
	
}