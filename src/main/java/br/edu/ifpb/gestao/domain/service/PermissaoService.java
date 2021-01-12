package br.edu.ifpb.gestao.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.gestao.domain.exception.PermissaoNaoEncontradaException;
import br.edu.ifpb.gestao.domain.model.Permissao;
import br.edu.ifpb.gestao.domain.repository.PermissaoRepository;

@Service
public class PermissaoService {
	
	@Autowired
	private PermissaoRepository permissaoRepository;
	
	public Permissao buscarOuFalhar(Long permissaoId) {
		return permissaoRepository.findById(permissaoId)
				.orElseThrow(() -> new PermissaoNaoEncontradaException(permissaoId));
	}
	
}