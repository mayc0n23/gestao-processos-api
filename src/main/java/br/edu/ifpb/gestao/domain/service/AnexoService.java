package br.edu.ifpb.gestao.domain.service;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.gestao.domain.exception.AnexoNaoEncontradoException;
import br.edu.ifpb.gestao.domain.model.Anexo;
import br.edu.ifpb.gestao.domain.model.Processo;
import br.edu.ifpb.gestao.domain.repository.AnexoRepository;
import br.edu.ifpb.gestao.domain.service.DocumentStorageService.NewDocument;
import br.edu.ifpb.gestao.domain.service.DocumentStorageService.RecoveredDocument;

@Service
public class AnexoService { //Classe utilizada para manipular os anexos
	
	@Autowired
	private ProcessoService processoService;
	
	@Autowired
	private AnexoRepository anexoRepository;
	
	@Autowired
	private DocumentStorageService documentStorage;
	
	@Transactional
	public Anexo adicionar(Long campusId, Anexo anexo, InputStream inputStream) {	
		Long processoId = anexo.getProcesso().getId();
		Processo processo = processoService.buscarOuFalhar(campusId, processoId);
		anexo.setProcesso(processo);
		
		String novoNomeArquivo = documentStorage.gerarNomeArquivo(anexo.getDocumento().getNomeArquivo());
		anexo.getDocumento().setNomeArquivo(novoNomeArquivo);
		
		NewDocument newDocument = NewDocument.builder()
				.nomeArquivo(anexo.getDocumento().getNomeArquivo())
				.contentType(anexo.getDocumento().getContentType())
				.inputStream(inputStream).build();
		
		documentStorage.armazenar(newDocument);
		RecoveredDocument recoveredDocument = documentStorage.recuperar(newDocument.getNomeArquivo());
		anexo.getDocumento().setUrl(recoveredDocument.getUrl());
		anexo =  anexoRepository.save(anexo);
		anexoRepository.flush();
		
		return anexo;
	}
	
	@Transactional
	public void excluir(Long campusId, Long processoId, Long anexoId) {
		Processo processo = processoService.buscarOuFalhar(campusId, processoId);
		Anexo anexo = buscarOuFalhar(processo.getId(), anexoId);
		anexoRepository.delete(anexo);
		anexoRepository.flush();
		documentStorage.remover(anexo.getDocumento().getNomeArquivo());
	}
	

	public Anexo buscarOuFalhar(Long processoId, Long anexoId) {
		return anexoRepository.findById(processoId, anexoId)
				.orElseThrow(() -> new AnexoNaoEncontradoException(anexoId));
	}
}