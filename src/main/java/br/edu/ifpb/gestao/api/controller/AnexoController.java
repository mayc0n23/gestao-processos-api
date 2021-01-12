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

import br.edu.ifpb.gestao.api.assembler.AnexoModelAssembler;
import br.edu.ifpb.gestao.api.disassembler.AnexoInputDisassembler;
import br.edu.ifpb.gestao.api.model.input.AnexoInputModel;
import br.edu.ifpb.gestao.api.model.input.DocumentoInputModel;
import br.edu.ifpb.gestao.api.model.input.IdProcessoInputModel;
import br.edu.ifpb.gestao.api.model.output.AnexoModel;
import br.edu.ifpb.gestao.domain.model.Anexo;
import br.edu.ifpb.gestao.domain.model.Documento;
import br.edu.ifpb.gestao.domain.model.Processo;
import br.edu.ifpb.gestao.domain.repository.AnexoRepository;
import br.edu.ifpb.gestao.domain.service.AnexoService;
import br.edu.ifpb.gestao.domain.service.ProcessoService;

@RestController
@RequestMapping("campus/{campusId}/processos/{processoId}/anexos")
public class AnexoController {
	
	@Autowired
	private ProcessoService processoService;
	
	@Autowired
	private AnexoService anexoService;
	
	@Autowired
	private AnexoModelAssembler anexoModelAssembler;
	
	@Autowired
	private AnexoInputDisassembler anexoInputDisassembler;
	
	@Autowired
	private AnexoRepository anexoRepository;
	
	@GetMapping
	public List<AnexoModel> listar(@PathVariable Long campusId, @PathVariable Long processoId) {
		Processo processo = processoService.buscarOuFalhar(campusId, processoId);
		return anexoModelAssembler.toCollectionModel(anexoRepository.findByProcesso(processo));
	}
	
	@GetMapping("/{anexoId}")
	public AnexoModel buscar(@PathVariable Long anexoId, @PathVariable Long processoId) {
		return anexoModelAssembler.toModel(anexoService.buscarOuFalhar(processoId, anexoId));
	}
	
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public AnexoModel adicionar(@PathVariable Long campusId, @PathVariable Long processoId, 
			@Valid DocumentoInputModel doc) throws IOException {
		Processo processo = processoService.buscarOuFalhar(campusId, processoId);
		MultipartFile arquivo = doc.getArquivo();
		
		doc.setContentType(arquivo.getContentType());
		doc.setNomeArquivo(arquivo.getOriginalFilename());
		
		Anexo novoAnexo = new Anexo();
		novoAnexo.setProcesso(processo);
		Documento documento = new Documento();
		documento.setContentType(arquivo.getContentType());
		documento.setNomeArquivo(arquivo.getOriginalFilename());
		novoAnexo.setDocumento(documento);
		
		IdProcessoInputModel processoInput = new IdProcessoInputModel();
		processoInput.setId(processo.getId());
		
		AnexoInputModel anexoInput = new AnexoInputModel();
		anexoInput.setDocumento(doc);
		anexoInput.setProcesso(processoInput);
		
		return anexoModelAssembler.toModel(anexoService.adicionar(campusId, anexoInputDisassembler.toDomainObject(anexoInput), 
				arquivo.getInputStream()));
	}
	
	@DeleteMapping("/{anexoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long campusId, @PathVariable Long processoId, @PathVariable Long anexoId) {
		anexoService.excluir(campusId, processoId, anexoId);
	}
	
}