package br.edu.ifpb.gestao.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifpb.gestao.api.model.output.DocumentoModel;
import br.edu.ifpb.gestao.domain.model.Documento;

@Component
public class DocumentoModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public DocumentoModel toModel(Documento documento) {
		return modelMapper.map(documento, DocumentoModel.class);
	}
	
	public List<DocumentoModel> toCollectionModel(List<Documento> documentos) {
		return documentos.stream()
				.map(documento -> toModel(documento))
				.collect(Collectors.toList());
	}
	
}