package br.edu.ifpb.gestao.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifpb.gestao.api.model.input.CampusInputModel;
import br.edu.ifpb.gestao.domain.model.Campus;
import br.edu.ifpb.gestao.domain.model.Usuario;

@Component
public class CampusInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Campus toDomainObject(CampusInputModel campusInput) {
		return modelMapper.map(campusInput, Campus.class);
	}
	
	public void copyToDomainObject(CampusInputModel campusInput, Campus campus) {
		campus.setResponsavel(new Usuario());
		modelMapper.map(campusInput, campus);
	}
	
}