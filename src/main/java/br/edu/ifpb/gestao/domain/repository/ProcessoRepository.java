package br.edu.ifpb.gestao.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.gestao.domain.model.Processo;

@Repository
public interface ProcessoRepository extends JpaRepository<Processo, Long> {
	
	@Query("from Processo where id = :processo and campus_id = :campus")
	Optional<Processo> findById(@Param("campus") Long campusId, @Param("processo") Long processoId);
	
	@Query("from Processo where campus_id = :campus")
	List<Processo> findAll(@Param("campus") Long campusId);
	
}