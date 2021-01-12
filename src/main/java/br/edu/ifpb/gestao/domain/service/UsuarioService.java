package br.edu.ifpb.gestao.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.gestao.domain.exception.NegocioException;
import br.edu.ifpb.gestao.domain.exception.UsuarioNaoEncontradoException;
import br.edu.ifpb.gestao.domain.model.Campus;
import br.edu.ifpb.gestao.domain.model.Grupo;
import br.edu.ifpb.gestao.domain.model.Usuario;
import br.edu.ifpb.gestao.domain.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private GrupoService grupoService;
	
	@Autowired
	private CampusService campusService;
	
	@Transactional
	public Usuario salvar(Usuario usuario) {
		usuarioRepository.detach(usuario);
		
		Optional<Usuario> usuarioExistenteComEmail = usuarioRepository.findByEmail(usuario.getEmail());
		if (usuarioExistenteComEmail.isPresent() && !usuarioExistenteComEmail.get().equals(usuario)) {
			throw new NegocioException(String.format("E-mail '%s' já está cadastrado", usuario.getEmail()));
		}
		
		Optional<Usuario> usuarioExistenteComMatricula = usuarioRepository.findByMatricula(usuario.getMatricula());
		if (usuarioExistenteComMatricula.isPresent() && !usuarioExistenteComMatricula.get().equals(usuario)) {
			throw new NegocioException(String.format("Matricula '%s' já está cadastrada", usuario.getMatricula()));
		}
		
		if (usuario.getCampus() != null) {
			if (usuario.getCampus().getId() != null) {
				Campus campus = campusService.buscarOuFalhar(usuario.getCampus().getId());
				usuario.setCampus(campus);
			}
		}
		
		if (usuario.isNovo()) {
			usuario.setSenha(usuario.getSenha());
		}
		
		return usuarioRepository.save(usuario);
	}
	
	@Transactional
	public void associar(Long usuarioId, Long grupoId) {
		Usuario usuario = buscarOuFalhar(usuarioId);
		Grupo grupo = grupoService.buscarOuFalhar(grupoId);
		
		usuario.getGrupos().add(grupo);
	}
	
	@Transactional
	public void desassociar(Long usuarioId, Long grupoId) {
		Usuario usuario = buscarOuFalhar(usuarioId);
		Grupo grupo = grupoService.buscarOuFalhar(grupoId);
		
		usuario.getGrupos().remove(grupo);
	}
	
	public Usuario buscarOuFalhar(Long usuarioId) {
		return usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
	}
	
}