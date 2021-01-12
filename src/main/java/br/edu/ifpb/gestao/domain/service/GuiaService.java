package br.edu.ifpb.gestao.domain.service;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.gestao.domain.exception.GuiaNaoEncontradoException;
import br.edu.ifpb.gestao.domain.model.Guia;
import br.edu.ifpb.gestao.domain.model.Processo;
import br.edu.ifpb.gestao.domain.repository.GuiaRepository;
import br.edu.ifpb.gestao.domain.service.DocumentStorageService.NewDocument;
import br.edu.ifpb.gestao.domain.service.DocumentStorageService.RecoveredDocument;

@Service
public class GuiaService {
	
	@Autowired
	private GuiaRepository guiaRepository;
	
	@Autowired
	private ProcessoService processoService;
	
	@Autowired
	private DocumentStorageService documentStorage;
	
	
	@Transactional
	public Guia adicionar(Long campusId, Guia guia, InputStream inputStream) {
		Long processoId = guia.getProcesso().getId();
		Processo processo = processoService.buscarOuFalhar(campusId, processoId);
		guia.setProcesso(processo);
		
		String novoNomeArquivo = documentStorage.gerarNomeArquivo(guia.getDocumento().getNomeArquivo());
		guia.getDocumento().setNomeArquivo(novoNomeArquivo);
		
		NewDocument newDocument = NewDocument.builder()
				.nomeArquivo(guia.getDocumento().getNomeArquivo())
				.contentType(guia.getDocumento().getContentType())
				.inputStream(inputStream).build();
		
		documentStorage.armazenar(newDocument);
		RecoveredDocument recoveredDocument = documentStorage.recuperar(newDocument.getNomeArquivo());
		guia.getDocumento().setUrl(recoveredDocument.getUrl());
		guia =  guiaRepository.save(guia);
		guiaRepository.flush();
		
		return guia;
	}
	
	@Transactional
	public void excluir(Long campusId, Long processoId, Long guiaId) {
		Processo processo = processoService.buscarOuFalhar(campusId, processoId);
		Guia guia = buscarOuFalhar(processo.getId(), guiaId);
		guiaRepository.delete(guia);
		guiaRepository.flush();
		documentStorage.remover(guia.getDocumento().getNomeArquivo());
	}
	
	public Guia buscarOuFalhar(Long processoId, Long guiaId) {
		return guiaRepository.findById(processoId, guiaId)
				.orElseThrow(() -> new GuiaNaoEncontradoException(guiaId));
	}
	
}