package br.edu.ifpb.gestao.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifpb.gestao.api.model.input.ProcessoInputModel;
import br.edu.ifpb.gestao.domain.model.Processo;

@Component
public class ProcessoInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Processo toDomainObject(ProcessoInputModel processoInput) {
		return modelMapper.map(processoInput, Processo.class);
	}
	
	public void copyToDomainObject(ProcessoInputModel processoInput, Processo processo) {
		
		
		modelMapper.map(processoInput, processo);
	}
	
}