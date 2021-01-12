package br.edu.ifpb.gestao.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.gestao.domain.exception.FaqNaoEncontradoException;
import br.edu.ifpb.gestao.domain.model.FAQ;
import br.edu.ifpb.gestao.domain.model.Processo;
import br.edu.ifpb.gestao.domain.repository.FAQRepository;

@Service
public class FAQService {
	
	@Autowired
	private ProcessoService processoService;
	
	@Autowired
	private FAQRepository faqRepository;
	
	@Transactional
	public FAQ adicionar(Long campusId, Long processoId, FAQ faq) {
		Processo processo = processoService.buscarOuFalhar(campusId, processoId);
		faq.setResposta("A pergunta ainda não foi respondida.");
		faq.setAtiva(false);
		faq.setProcesso(processo);
		return faqRepository.save(faq);
	}
	
	@Transactional
	public void excluir(Long campusId, Long processoId, Long faqId) {
		Processo processo = processoService.buscarOuFalhar(campusId, processoId);
		FAQ faq = buscarOuFalhar(processo.getId(), faqId);
		faqRepository.delete(faq);
	}
	
	public FAQ buscarOuFalhar(Long processoId, Long faqId) {
		return faqRepository.findById(processoId, faqId)
				.orElseThrow(() -> new FaqNaoEncontradoException(faqId));
	}
	
	@Transactional
	public void ativar(Long campusId, Long processoId, Long faqId) {
		Processo processo = processoService.buscarOuFalhar(campusId, processoId);
		FAQ faq = buscarOuFalhar(processo.getId(), faqId);
		faq.setAtiva(true);
	}
	
	@Transactional
	public void inativar(Long campusId, Long processoId, Long faqId) {
		Processo processo = processoService.buscarOuFalhar(campusId, processoId);
		FAQ faq = buscarOuFalhar(processo.getId(), faqId);
		faq.setAtiva(false);
	}
	
}