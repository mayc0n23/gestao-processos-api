package br.edu.ifpb.gestao.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifpb.gestao.api.model.input.UsuarioInputModel;
import br.edu.ifpb.gestao.domain.model.Campus;
import br.edu.ifpb.gestao.domain.model.Usuario;

@Component
public class UsuarioInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Usuario toDomainObject(UsuarioInputModel usuarioInput) {
		return modelMapper.map(usuarioInput, Usuario.class);
	}
	
	public void copyToDomainObject(UsuarioInputModel usuarioInput, Usuario usuario) {
		usuario.setCampus(new Campus());
	
		modelMapper.map(usuarioInput, usuario);
	}
	
}