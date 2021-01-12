package br.edu.ifpb.gestao.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifpb.gestao.api.model.input.DocumentoInputModel;
import br.edu.ifpb.gestao.domain.model.Documento;

@Component
public class DocumentoInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Documento toDomainObject(DocumentoInputModel documentoInput) {
		return modelMapper.map(documentoInput, Documento.class);
	}
	
}