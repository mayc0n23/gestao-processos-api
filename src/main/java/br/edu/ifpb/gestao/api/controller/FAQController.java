package br.edu.ifpb.gestao.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.gestao.api.assembler.FAQModelAssembler;
import br.edu.ifpb.gestao.api.disassembler.FAQInputDisassembler;
import br.edu.ifpb.gestao.api.model.input.FAQInputModel;
import br.edu.ifpb.gestao.api.model.output.FAQModel;
import br.edu.ifpb.gestao.domain.model.Processo;
import br.edu.ifpb.gestao.domain.repository.FAQRepository;
import br.edu.ifpb.gestao.domain.service.FAQService;
import br.edu.ifpb.gestao.domain.service.ProcessoService;

@RestController
@RequestMapping("campus/{campusId}/processos/{processoId}/faqs")
public class FAQController {
	
	@Autowired
	private FAQService faqService;
	
	@Autowired
	private FAQModelAssembler faqModelAssembler;
	
	@Autowired
	private FAQInputDisassembler faqInputDisassembler;
	
	@Autowired
	private FAQRepository faqRepository;
	
	@Autowired
	private ProcessoService processoService;
	
	@GetMapping
	public List<FAQModel> listar(@PathVariable Long campusId, @PathVariable Long processoId) {
		Processo processo = processoService.buscarOuFalhar(campusId, processoId);
		return faqModelAssembler.toCollectionModel(faqRepository.findByProcesso(processo));
	}
	
	@GetMapping("/{faqId}")
	public FAQModel buscar(@PathVariable Long faqId, @PathVariable Long processoId) {
		return faqModelAssembler.toModel(faqService.buscarOuFalhar(processoId, faqId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FAQModel adicionar(@PathVariable Long campusId, @PathVariable Long processoId, 
			@RequestBody @Valid FAQInputModel faq) {
		return faqModelAssembler.toModel(faqService.adicionar(campusId, processoId, faqInputDisassembler.toDomainObject(faq)));
	}
	
	@DeleteMapping("/{faqId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long campusId, @PathVariable Long processoId, @PathVariable Long faqId) {
		faqService.excluir(campusId, processoId, faqId);
	}
	
	@PutMapping("/{faqId}/ativar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarPergunta(@PathVariable Long campusId, @PathVariable Long processoId, @PathVariable Long faqId) {
		faqService.ativar(campusId, processoId, faqId);
	}
	
	@DeleteMapping("/{faqId}/inativar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativarPergunta(@PathVariable Long campusId, @PathVariable Long processoId, @PathVariable Long faqId) {
		faqService.inativar(campusId, processoId, faqId);
	}

}