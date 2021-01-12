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

import br.edu.ifpb.gestao.domain.model.Documento;
import br.edu.ifpb.gestao.domain.model.Guia;
import br.edu.ifpb.gestao.domain.model.Processo;
import br.edu.ifpb.gestao.domain.repository.GuiaRepository;
import br.edu.ifpb.gestao.domain.service.GuiaService;
import br.edu.ifpb.gestao.domain.service.ProcessoService;
import br.edu.ifpb.gestao.api.assembler.GuiaModelAssembler;
import br.edu.ifpb.gestao.api.disassembler.GuiaInputDisassembler;
import br.edu.ifpb.gestao.api.model.input.DocumentoInputModel;
import br.edu.ifpb.gestao.api.model.input.GuiaInputModel;
import br.edu.ifpb.gestao.api.model.input.IdProcessoInputModel;
import br.edu.ifpb.gestao.api.model.output.GuiaModel;

@RestController
@RequestMapping("campus/{campusId}/processos/{processoId}/guias")
public class GuiaController {
	
	@Autowired
	private GuiaService guiaService;
	
	@Autowired
	private GuiaInputDisassembler guiaInputDisassembler;
	
	@Autowired
	private GuiaModelAssembler guiaModelAssembler;
	
	@Autowired
	private ProcessoService processoService;
	
	@Autowired
	private GuiaRepository guiaRepository;
	
	@GetMapping
	public List<GuiaModel> listar(@PathVariable Long campusId, @PathVariable Long processoId) {
		Processo processo = processoService.buscarOuFalhar(campusId, processoId);
		return guiaModelAssembler.toCollectionModel(guiaRepository.findByProcesso(processo));
	}
	
	@GetMapping("/{guiaId}")
	public GuiaModel buscar(@PathVariable Long processoId, @PathVariable Long guiaId) {
		return guiaModelAssembler.toModel(guiaService.buscarOuFalhar(processoId, guiaId));
	}
	
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public GuiaModel adicionar(@PathVariable Long campusId, @PathVariable Long processoId, 
			@Valid DocumentoInputModel doc) throws IOException {
		Processo processo = processoService.buscarOuFalhar(campusId, processoId);
		MultipartFile arquivo = doc.getArquivo();
		
		doc.setContentType(arquivo.getContentType());
		doc.setNomeArquivo(arquivo.getOriginalFilename());
		
		Guia novaGuia = new Guia();
		novaGuia.setProcesso(processo);
		Documento documento = new Documento();
		documento.setContentType(arquivo.getContentType());
		documento.setNomeArquivo(arquivo.getOriginalFilename());
		novaGuia.setDocumento(documento);
		
		IdProcessoInputModel processoInput = new IdProcessoInputModel();
		processoInput.setId(processo.getId());
		
		GuiaInputModel guiaInput = new GuiaInputModel();
		guiaInput.setDocumento(doc);
		guiaInput.setProcesso(processoInput);
		
		return guiaModelAssembler.toModel(guiaService.adicionar(campusId, guiaInputDisassembler.toDomainObject(guiaInput), 
				arquivo.getInputStream()));
	}
	
	@DeleteMapping("/{guiaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long campusId, @PathVariable Long processoId, @PathVariable Long guiaId) {
		guiaService.excluir(campusId, processoId, guiaId);
	}
}