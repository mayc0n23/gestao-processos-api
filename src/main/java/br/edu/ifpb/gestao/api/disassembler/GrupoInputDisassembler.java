package br.edu.ifpb.gestao.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifpb.gestao.api.model.input.GrupoInputModel;
import br.edu.ifpb.gestao.domain.model.Grupo;

@Component
public class GrupoInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Grupo toDomainObject(GrupoInputModel grupoInput) {
		return modelMapper.map(grupoInput, Grupo.class);
	}
	
}