package br.edu.ifpb.gestao.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.edu.ifpb.gestao.domain.model.Protocolo;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CampusInputModel {
	
	@NotBlank
	private String nomeCampus;
	
	@NotBlank
	private String nomeInstituicao;
	
	@NotNull
	private Protocolo protocolo;
	
	@NotNull
	@Valid
	private UsuarioIdInputModel responsavel;
	
}