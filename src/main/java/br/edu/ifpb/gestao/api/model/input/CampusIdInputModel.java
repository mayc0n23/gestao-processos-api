package br.edu.ifpb.gestao.api.model.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CampusIdInputModel {
	
	@NotNull
	private Long id;
	
}