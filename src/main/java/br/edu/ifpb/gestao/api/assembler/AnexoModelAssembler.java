package br.edu.ifpb.gestao.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifpb.gestao.api.model.output.AnexoModel;
import br.edu.ifpb.gestao.domain.model.Anexo;

@Component
public class AnexoModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public AnexoModel toModel(Anexo anexo) {
		return modelMapper.map(anexo, AnexoModel.class);
	}
	
	public List<AnexoModel> toCollectionModel(List<Anexo> anexos) {
		return anexos.stream()
				.map(anexo -> toModel(anexo))
				.collect(Collectors.toList());
	}
	
}