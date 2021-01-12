package br.edu.ifpb.gestao.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifpb.gestao.api.model.input.GuiaInputModel;
import br.edu.ifpb.gestao.domain.model.Guia;

@Component
public class GuiaInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Guia toDomainObject(GuiaInputModel guiaInput) {
		return modelMapper.map(guiaInput, Guia.class);
	}
	
}