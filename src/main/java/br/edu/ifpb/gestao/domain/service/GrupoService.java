package br.edu.ifpb.gestao.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.gestao.domain.exception.EntidadeEmUsoException;
import br.edu.ifpb.gestao.domain.exception.GrupoNaoEncontradoException;
import br.edu.ifpb.gestao.domain.model.Grupo;
import br.edu.ifpb.gestao.domain.model.Permissao;
import br.edu.ifpb.gestao.domain.repository.GrupoRepository;

@Service
public class GrupoService {
	
	private final String MSG_GRUPO_EM_USO = "Grupo de código '%d' não pode ser removido, pois está em uso";
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private PermissaoService permissaoService;
	
	@Transactional
	public Grupo salvar(Grupo grupo) {
		return grupoRepository.save(grupo);
	}
	
	@Transactional
	public void excluir(Long grupoId) {
		try {
			grupoRepository.deleteById(grupoId);
			grupoRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new GrupoNaoEncontradoException(grupoId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(MSG_GRUPO_EM_USO);
		}
	}
	
	public Grupo buscarOuFalhar(Long grupoId) {
		return grupoRepository.findById(grupoId)
				.orElseThrow(() -> new GrupoNaoEncontradoException(grupoId));
	}
	
	@Transactional
	public void desassociarPermissao(Long grupoId, Long permissaoId) {
		Grupo grupo = buscarOuFalhar(grupoId);
		Permissao permissao = permissaoService.buscarOuFalhar(permissaoId);
		
		grupo.removerPermissao(permissao);
	}
	
	@Transactional
	public void associarPermissao(Long grupoId, Long permissaoId) {
		Grupo grupo = buscarOuFalhar(grupoId);
		Permissao permissao = permissaoService.buscarOuFalhar(permissaoId);
		
		grupo.adicionarPermissao(permissao);
	}
	
}