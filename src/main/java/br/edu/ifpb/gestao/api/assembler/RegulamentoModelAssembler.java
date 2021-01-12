package br.edu.ifpb.gestao.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifpb.gestao.api.model.output.RegulamentoModel;
import br.edu.ifpb.gestao.domain.model.Regulamento;

@Component
public class RegulamentoModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public RegulamentoModel toModel(Regulamento regulamento) {
		return modelMapper.map(regulamento, RegulamentoModel.class);
	}
	
	public List<RegulamentoModel> toCollectionModel(List<Regulamento> regulamentos) {
		return regulamentos.stream()
				.map(regulamento -> toModel(regulamento))
				.collect(Collectors.toList());
	}
	
}