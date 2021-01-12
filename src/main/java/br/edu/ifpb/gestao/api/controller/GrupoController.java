package br.edu.ifpb.gestao.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.gestao.api.assembler.GrupoModelAssembler;
import br.edu.ifpb.gestao.api.disassembler.GrupoInputDisassembler;
import br.edu.ifpb.gestao.api.model.input.GrupoInputModel;
import br.edu.ifpb.gestao.api.model.output.GrupoModel;
import br.edu.ifpb.gestao.domain.repository.GrupoRepository;
import br.edu.ifpb.gestao.domain.service.GrupoService;

@RestController
@RequestMapping("/grupos")
public class GrupoController {
	
	@Autowired
	private GrupoService grupoService;
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private GrupoModelAssembler grupoModelAssembler;
	
	@Autowired
	private GrupoInputDisassembler grupoInputDisassembler;
	
	@GetMapping
	public List<GrupoModel> listar() {
		return grupoModelAssembler.toCollectionModel(grupoRepository.findAll());
	}
	
	@GetMapping("/{grupoId}")
	public GrupoModel buscar(@PathVariable Long grupoId) {
		return grupoModelAssembler.toModel(grupoService.buscarOuFalhar(grupoId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoModel cadastrar(@RequestBody @Valid GrupoInputModel grupoInput) {
		return grupoModelAssembler.toModel(grupoService.salvar(grupoInputDisassembler.toDomainObject(grupoInput)));
	}
	
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long grupoId) {
		grupoService.excluir(grupoId);
	}
	
}