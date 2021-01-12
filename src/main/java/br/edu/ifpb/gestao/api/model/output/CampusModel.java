package br.edu.ifpb.gestao.api.model.output;

import br.edu.ifpb.gestao.domain.model.Protocolo;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CampusModel {
	
	private Long id;
	
	private String nomeCampus;
	
	private String nomeInstituicao;
	
	private Protocolo protocolo;
	
	private UsuarioModel responsavel;
	
}