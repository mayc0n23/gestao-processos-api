package br.edu.ifpb.gestao.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.gestao.api.assembler.AnexoModelAssembler;
import br.edu.ifpb.gestao.api.assembler.FAQModelAssembler;
import br.edu.ifpb.gestao.api.assembler.GuiaModelAssembler;
import br.edu.ifpb.gestao.api.assembler.ProcessoListModelAssembler;
import br.edu.ifpb.gestao.api.assembler.ProcessoModelAssembler;
import br.edu.ifpb.gestao.api.assembler.RegulamentoModelAssembler;
import br.edu.ifpb.gestao.api.disassembler.ProcessoInputDisassembler;
import br.edu.ifpb.gestao.api.model.input.ProcessoInputModel;
import br.edu.ifpb.gestao.api.model.output.ProcessoListModel;
import br.edu.ifpb.gestao.api.model.output.ProcessoModel;
import br.edu.ifpb.gestao.domain.model.Processo;
import br.edu.ifpb.gestao.domain.repository.AnexoRepository;
import br.edu.ifpb.gestao.domain.repository.FAQRepository;
import br.edu.ifpb.gestao.domain.repository.GuiaRepository;
import br.edu.ifpb.gestao.domain.repository.RegulamentoRepository;
import br.edu.ifpb.gestao.domain.service.ProcessoService;

@RestController
@RequestMapping("/campus/{campusId}/processos")
public class ProcessoController {
	
	@Autowired
	private GuiaRepository guiaRepository;
	
	@Autowired
	private AnexoRepository anexoRepository;
	
	@Autowired
	private RegulamentoRepository regulamentoRepository;
	
	@Autowired
	private FAQRepository faqRepository;
	
	@Autowired
	private ProcessoService processoService;
	
	@Autowired
	private ProcessoModelAssembler processoModelAssembler;
	
	@Autowired
	private ProcessoInputDisassembler processoInputDisassembler;
	
	@Autowired
	private ProcessoListModelAssembler processoListModelAssembler;
	
	@Autowired
	private AnexoModelAssembler anexoModelAssembler;
	
	@Autowired
	private GuiaModelAssembler guiaModelAssembler;
	
	@Autowired
	private RegulamentoModelAssembler regulamentoModelAssembler;
	
	@Autowired
	private FAQModelAssembler faqModelAssembler;
	
	
	@GetMapping
	public List<ProcessoListModel> listar(@PathVariable Long campusId) {
		return processoListModelAssembler.toCollectionModel(processoService.listar(campusId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProcessoModel adicionar(@PathVariable Long campusId, @RequestBody @Valid ProcessoInputModel processo) {
		Processo processoSalvo = processoService.salvar(campusId, processoInputDisassembler.toDomainObject(processo));
		return processoModelAssembler.toModel(processoSalvo);
	}
	
	@GetMapping("/{processoId}")
	public ProcessoModel buscar(@PathVariable Long campusId, @PathVariable Long processoId) {
		Processo processo = processoService.buscarOuFalhar(campusId, processoId);
		ProcessoModel processoModel =  processoModelAssembler.toModel(processo);
		processoModel.setAnexos(anexoModelAssembler.toCollectionModel(anexoRepository.findByProcesso(processo)));
		processoModel.setGuias(guiaModelAssembler.toCollectionModel(guiaRepository.findByProcesso(processo)));
		processoModel.setRegulamentos(regulamentoModelAssembler.toCollectionModel(regulamentoRepository.findByProcesso(processo)));
		processoModel.setFaqs(faqModelAssembler.toCollectionModel(faqRepository.findByProcesso(processo)));
		return processoModel;
	}
	
}