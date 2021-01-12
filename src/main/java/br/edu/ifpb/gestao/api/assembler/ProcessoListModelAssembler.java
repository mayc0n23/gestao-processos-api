package br.edu.ifpb.gestao.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifpb.gestao.api.model.output.ProcessoListModel;
import br.edu.ifpb.gestao.domain.model.Processo;

@Component
public class ProcessoListModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public ProcessoListModel toModel(Processo processo) {
		return modelMapper.map(processo, ProcessoListModel.class);
	}
	
	public List<ProcessoListModel> toCollectionModel(List<Processo> processos) {
		return processos.stream()
				.map(processo -> toModel(processo))
				.collect(Collectors.toList());
	}
	
}