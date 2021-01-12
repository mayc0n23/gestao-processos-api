package br.edu.ifpb.gestao.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifpb.gestao.api.model.output.CampusModel;
import br.edu.ifpb.gestao.domain.model.Campus;

@Component
public class CampusModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public CampusModel toModel(Campus campus) {
		return modelMapper.map(campus, CampusModel.class);
	}
	
	public List<CampusModel> toCollectionModel(List<Campus> campus) {
		return campus.stream()
				.map(camp -> toModel(camp))
				.collect(Collectors.toList());
	}
	
}