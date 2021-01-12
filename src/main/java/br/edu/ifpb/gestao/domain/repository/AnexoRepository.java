package br.edu.ifpb.gestao.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.gestao.domain.model.Anexo;
import br.edu.ifpb.gestao.domain.model.Processo;

@Repository
public interface AnexoRepository extends JpaRepository<Anexo, Long> {
	
	@Query("from Anexo where processo.id = :processo and id = :anexo")
	Optional<Anexo> findById(@Param("processo") Long processoId, @Param("anexo") Long anexoId);
	
	List<Anexo> findByProcesso(Processo processo);
	
}