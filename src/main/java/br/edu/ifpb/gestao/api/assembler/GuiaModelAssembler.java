package br.edu.ifpb.gestao.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifpb.gestao.api.model.output.GuiaModel;
import br.edu.ifpb.gestao.domain.model.Guia;

@Component
public class GuiaModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public GuiaModel toModel(Guia guia) {
		return modelMapper.map(guia, GuiaModel.class);
	}
	
	public List<GuiaModel> toCollectionModel(List<Guia> guias) {
		return guias.stream()
				.map(guia -> toModel(guia))
				.collect(Collectors.toList());
	}
	
}