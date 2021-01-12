package br.edu.ifpb.gestao.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.gestao.domain.exception.ProcessoNaoEncontradoException;
import br.edu.ifpb.gestao.domain.model.Campus;
import br.edu.ifpb.gestao.domain.model.Processo;
import br.edu.ifpb.gestao.domain.repository.ProcessoRepository;

@Service
public class ProcessoService {
	
	@Autowired
	private ProcessoRepository processoRepository;
	
	@Autowired
	private CampusService campusService;
	
	@Transactional
	public Processo salvar(Long campusId, Processo processo) {
		Campus campus = campusService.buscarOuFalhar(campusId);
		processo.setCampus(campus);
		return processoRepository.save(processo);
	}
	
	public Processo buscarOuFalhar(Long campusId, Long processoId) {
		Campus campus = campusService.buscarOuFalhar(campusId);
		return processoRepository.findById(campus.getId(), processoId)
				.orElseThrow(() -> new ProcessoNaoEncontradoException(campusId, processoId));
	}
	
	public List<Processo> listar(Long campusId) {
		Campus campus = campusService.buscarOuFalhar(campusId);
		return processoRepository.findAll(campus.getId());
	}
	
}