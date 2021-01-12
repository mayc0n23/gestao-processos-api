package br.edu.ifpb.gestao.api.model.input;

import javax.validation.Valid;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GuiaInputModel {
	
	@Valid
	private DocumentoInputModel documento;
	
	private IdProcessoInputModel processo;
	
}