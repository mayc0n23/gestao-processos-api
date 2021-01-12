package br.edu.ifpb.gestao.domain.service;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.gestao.domain.exception.RegulamentoNaoEncontradoException;
import br.edu.ifpb.gestao.domain.model.Processo;
import br.edu.ifpb.gestao.domain.repository.RegulamentoRepository;
import br.edu.ifpb.gestao.domain.service.DocumentStorageService.NewDocument;
import br.edu.ifpb.gestao.domain.service.DocumentStorageService.RecoveredDocument;
import br.edu.ifpb.gestao.domain.model.Regulamento;

@Service
public class RegulamentoService {
	
	@Autowired
	private RegulamentoRepository regulamentoRepository;
	
	@Autowired
	private ProcessoService processoService;
	
	@Autowired
	private DocumentStorageService documentStorage;
	
	@Transactional
	public Regulamento adicionar(Long campusId, Regulamento regulamento, InputStream inputStream) {
		Long processoId = regulamento.getProcesso().getId();
		Processo processo = processoService.buscarOuFalhar(campusId, processoId);
		regulamento.setProcesso(processo);
		
		String novoNomeArquivo = documentStorage.gerarNomeArquivo(regulamento.getDocumento().getNomeArquivo());
		regulamento.getDocumento().setNomeArquivo(novoNomeArquivo);
		
		NewDocument newDocument = NewDocument.builder()
				.nomeArquivo(regulamento.getDocumento().getNomeArquivo())
				.contentType(regulamento.getDocumento().getContentType())
				.inputStream(inputStream).build();
		
		documentStorage.armazenar(newDocument);
		RecoveredDocument recoveredDocument = documentStorage.recuperar(newDocument.getNomeArquivo());
		regulamento.getDocumento().setUrl(recoveredDocument.getUrl());
		regulamento = regulamentoRepository.save(regulamento);
		regulamentoRepository.flush();
		
		return regulamento;
	}
	
	@Transactional
	public void excluir(Long campusId, Long processoId, Long regulamentoId) {
		Processo processo = processoService.buscarOuFalhar(campusId, processoId);
		Regulamento regulamento = buscarOuFalhar(processo.getId(), regulamentoId);
		regulamentoRepository.delete(regulamento);
		regulamentoRepository.flush();
		documentStorage.remover(regulamento.getDocumento().getNomeArquivo());
	}
	
	public Regulamento buscarOuFalhar(Long processoId, Long regulamentoId) {
		return regulamentoRepository.findById(processoId, regulamentoId)
				.orElseThrow(() -> new RegulamentoNaoEncontradoException(regulamentoId));
	}
	
}