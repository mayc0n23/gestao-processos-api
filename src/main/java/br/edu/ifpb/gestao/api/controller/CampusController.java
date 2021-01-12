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

import br.edu.ifpb.gestao.api.assembler.CampusModelAssembler;
import br.edu.ifpb.gestao.api.assembler.UsuarioModelAssembler;
import br.edu.ifpb.gestao.api.disassembler.CampusInputDisassembler;
import br.edu.ifpb.gestao.api.model.input.CampusInputModel;
import br.edu.ifpb.gestao.api.model.output.CampusModel;
import br.edu.ifpb.gestao.api.model.output.UsuarioModel;
import br.edu.ifpb.gestao.domain.model.Campus;
import br.edu.ifpb.gestao.domain.repository.CampusRepository;
import br.edu.ifpb.gestao.domain.repository.UsuarioRepository;
import br.edu.ifpb.gestao.domain.service.CampusService;

@RestController
@RequestMapping("/campus")
public class CampusController {
	
	@Autowired
	private CampusService campusService;
	
	@Autowired
	private CampusModelAssembler campusModelAssembler;
	
	@Autowired
	private CampusInputDisassembler campusInputDisassembler;
	
	@Autowired
	private CampusRepository campusRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioModelAssembler usuarioModelAssembler;
	
	@GetMapping
	public List<CampusModel> listar() {
		return campusModelAssembler.toCollectionModel(campusRepository.findAll());
	}
	
	@GetMapping("/{campusId}")
	public CampusModel buscar(@PathVariable Long campusId) {
		return campusModelAssembler.toModel(campusService.buscarOuFalhar(campusId));
	}
	
	@GetMapping("/{campusId}/usuarios")
	public List<UsuarioModel> listarUsuariosDoCampus(@PathVariable Long campusId) {
		Campus campus = campusService.buscarOuFalhar(campusId);
		List<UsuarioModel> usuarios = usuarioModelAssembler.toCollectionModel(usuarioRepository.findByCampus(campus));
		usuarios.add(0, usuarioModelAssembler.toModel(campus.getResponsavel()));
		return usuarios;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CampusModel adicionar(@RequestBody @Valid CampusInputModel campusInput) {
		return campusModelAssembler.toModel(campusService.salvar(campusInputDisassembler.toDomainObject(campusInput)));
	}
	
	@PutMapping("/{campusId}")
	public CampusModel editar(@PathVariable Long campusId, @RequestBody @Valid CampusInputModel campusInput) {
		Campus campusAtual = campusService.buscarOuFalhar(campusId);
		
		campusInputDisassembler.copyToDomainObject(campusInput, campusAtual);
		
		return campusModelAssembler.toModel(campusService.salvar(campusAtual));
	}
	
}