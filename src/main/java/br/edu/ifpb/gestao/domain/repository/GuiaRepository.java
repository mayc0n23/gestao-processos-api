package br.edu.ifpb.gestao.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.gestao.domain.model.Guia;
import br.edu.ifpb.gestao.domain.model.Processo;

@Repository
public interface GuiaRepository extends JpaRepository<Guia, Long> {
	
	@Query("from Guia where processo.id = :processo and id = :guia")
	Optional<Guia> findById(@Param("processo") Long processoId, @Param("guia") Long guiaId);
	
	List<Guia> findByProcesso(Processo processo);
	
}