package br.edu.ifpb.gestao.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.gestao.domain.model.FAQ;
import br.edu.ifpb.gestao.domain.model.Processo;

@Repository
public interface FAQRepository extends JpaRepository<FAQ, Long> {
	
	@Query(value = "select * from faq where processo_id = :processo and id = :faqid", nativeQuery = true)
	Optional<FAQ> findById(@Param("processo") Long processoId, @Param("faqid") Long faqId);
	
	List<FAQ> findByProcesso(Processo processo);

}