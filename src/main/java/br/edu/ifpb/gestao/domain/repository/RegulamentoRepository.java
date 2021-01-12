package br.edu.ifpb.gestao.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.gestao.domain.model.Processo;
import br.edu.ifpb.gestao.domain.model.Regulamento;

@Repository
public interface RegulamentoRepository extends JpaRepository<Regulamento, Long> {
	
	@Query("from Regulamento where processo.id = :processo and id = :regulamento")
	Optional<Regulamento> findById(@Param("processo") Long processoId, @Param("regulamento") Long regulamentoId);
	
	List<Regulamento> findByProcesso(Processo processo);
	
	
}