package br.edu.ifpb.gestao.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.edu.ifpb.gestao.domain.model.Campus;
import br.edu.ifpb.gestao.domain.model.Usuario;

@Repository
public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long> {
	
	Optional<Usuario> findByEmail(String email);
	
	Optional<Usuario> findByMatricula(String matricula);
	
	List<Usuario> findByCampus(Campus campus);
	
}