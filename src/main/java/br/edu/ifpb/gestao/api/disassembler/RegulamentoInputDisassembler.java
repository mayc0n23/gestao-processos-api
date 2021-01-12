package br.edu.ifpb.gestao.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifpb.gestao.api.model.input.RegulamentoInputModel;
import br.edu.ifpb.gestao.domain.model.Regulamento;

@Component
public class RegulamentoInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Regulamento toDomainObject(RegulamentoInputModel regulamentoInput) {
		return modelMapper.map(regulamentoInput, Regulamento.class);
	}
	
}