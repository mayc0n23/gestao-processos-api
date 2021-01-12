package br.edu.ifpb.gestao.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.gestao.api.assembler.UsuarioModelAssembler;
import br.edu.ifpb.gestao.api.disassembler.UsuarioInputDisassembler;
import br.edu.ifpb.gestao.api.model.input.UsuarioInputModel;
import br.edu.ifpb.gestao.api.model.output.UsuarioModel;
import br.edu.ifpb.gestao.domain.model.Usuario;
import br.edu.ifpb.gestao.domain.repository.UsuarioRepository;
import br.edu.ifpb.gestao.domain.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioModelAssembler usuarioModelAssembler;
	
	@Autowired
	private UsuarioInputDisassembler usuarioInputDisassembler;
	
	@GetMapping
	public List<UsuarioModel> listar() {
		return usuarioModelAssembler.toCollectionModel(usuarioRepository.findAll());
	}
	
	@GetMapping("/{usuarioId}")
	public UsuarioModel buscar(@PathVariable Long usuarioId) {
		return usuarioModelAssembler.toModel(usuarioService.buscarOuFalhar(usuarioId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioModel adicionar(@RequestBody @Valid UsuarioInputModel usuarioInput) {
		return usuarioModelAssembler.toModel(usuarioService.salvar(usuarioInputDisassembler.toDomainObject(usuarioInput)));
	}
	
	@PutMapping("/{usuarioId}")
	public UsuarioModel editar(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioInputModel usuarioInput) {
		Usuario usuarioAtual = usuarioService.buscarOuFalhar(usuarioId);
		
		usuarioInputDisassembler.copyToDomainObject(usuarioInput, usuarioAtual);
		
		return usuarioModelAssembler.toModel(usuarioService.salvar(usuarioAtual));
	}
	
}