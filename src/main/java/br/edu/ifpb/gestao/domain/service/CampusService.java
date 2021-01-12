package br.edu.ifpb.gestao.domain.service;

import br.edu.ifpb.gestao.domain.exception.CampusNaoEncontradoException;
import br.edu.ifpb.gestao.domain.model.Campus;
import br.edu.ifpb.gestao.domain.model.Usuario;
import br.edu.ifpb.gestao.domain.repository.CampusRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CampusService {
	
	@Autowired
	private CampusRepository campusRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Transactional
	public Campus salvar(Campus campus) {
		Usuario responsavel = usuarioService.buscarOuFalhar(campus.getResponsavel().getId());
		campus.setResponsavel(responsavel);
		return campusRepository.save(campus);
	}
	
	public Campus buscarOuFalhar(Long campusId) {
		return campusRepository.findById(campusId)
				.orElseThrow(() -> new CampusNaoEncontradoException(campusId));
	}
	
}