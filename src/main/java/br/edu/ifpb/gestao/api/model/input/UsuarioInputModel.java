package br.edu.ifpb.gestao.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioInputModel {
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String email;
	
	@NotBlank
	private String matricula;
	
	@NotBlank
	private String senha;
	
	@Valid
	private CampusIdInputModel campus;
	
}