package br.edu.ifpb.gestao.api.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.edu.ifpb.gestao.api.assembler.RegulamentoModelAssembler;
import br.edu.ifpb.gestao.api.disassembler.RegulamentoInputDisassembler;
import br.edu.ifpb.gestao.api.model.input.DocumentoInputModel;
import br.edu.ifpb.gestao.api.model.input.IdProcessoInputModel;
import br.edu.ifpb.gestao.api.model.input.RegulamentoInputModel;
import br.edu.ifpb.gestao.api.model.output.RegulamentoModel;
import br.edu.ifpb.gestao.domain.model.Documento;
import br.edu.ifpb.gestao.domain.model.Processo;
import br.edu.ifpb.gestao.domain.model.Regulamento;
import br.edu.ifpb.gestao.domain.repository.RegulamentoRepository;
import br.edu.ifpb.gestao.domain.service.ProcessoService;
import br.edu.ifpb.gestao.domain.service.RegulamentoService;

@RestController
@RequestMapping("campus/{campusId}/processos/{processoId}/regulamentos")
public class RegulamentoController {
	
	@Autowired
	private RegulamentoService regulamentoService;
	
	@Autowired
	private RegulamentoModelAssembler regulamentoModelAssembler;
	
	@Autowired
	private RegulamentoInputDisassembler regulamentoInputDisassembler;
	
	@Autowired
	private ProcessoService processoService;
	
	@Autowired
	private RegulamentoRepository regulamentoRepository;
	
	@GetMapping
	public List<RegulamentoModel> listar(@PathVariable Long campusId, @PathVariable Long processoId) {
		Processo processo = processoService.buscarOuFalhar(campusId, processoId);
		return regulamentoModelAssembler.toCollectionModel(regulamentoRepository.findByProcesso(processo));
	}
	
	public RegulamentoModel buscar(@PathVariable Long regulamentoId, @PathVariable Long processoId) {
		return regulamentoModelAssembler.toModel(regulamentoService.buscarOuFalhar(processoId, regulamentoId));
	}
	
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public RegulamentoModel adicionar(@PathVariable Long campusId, @PathVariable Long processoId, 
			@Valid DocumentoInputModel doc) throws IOException {
		Processo processo = processoService.buscarOuFalhar(campusId, processoId);
		MultipartFile arquivo = doc.getArquivo();
		doc.setContentType(arquivo.getContentType());
		doc.setNomeArquivo(arquivo.getOriginalFilename());
		Regulamento novoRegulamento = new Regulamento();
		novoRegulamento.setProcesso(processo);
		Documento documento = new Documento();
		documento.setContentType(arquivo.getContentType());
		documento.setNomeArquivo(arquivo.getOriginalFilename());
		novoRegulamento.setDocumento(documento);
		
		IdProcessoInputModel processoInput = new IdProcessoInputModel();
		processoInput.setId(processo.getId());
		
		RegulamentoInputModel regulamentoInput = new RegulamentoInputModel();
		regulamentoInput.setDocumento(doc);
		regulamentoInput.setProcesso(processoInput);
		
		return regulamentoModelAssembler.toModel(
				regulamentoService.adicionar(campusId, regulamentoInputDisassembler.toDomainObject(regulamentoInput), 
						arquivo.getInputStream()));
	}
	
	@DeleteMapping("/{regulamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long campusId, @PathVariable Long processoId, @PathVariable Long regulamentoId) {
		regulamentoService.excluir(campusId, processoId, regulamentoId);
	}
	
}