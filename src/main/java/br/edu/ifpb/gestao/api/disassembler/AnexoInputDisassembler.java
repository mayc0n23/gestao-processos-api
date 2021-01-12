package br.edu.ifpb.gestao.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifpb.gestao.api.model.input.AnexoInputModel;
import br.edu.ifpb.gestao.domain.model.Anexo;

@Component
public class AnexoInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Anexo toDomainObject(AnexoInputModel anexoInput) {
		return modelMapper.map(anexoInput, Anexo.class);
	}
	
}