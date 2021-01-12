package br.edu.ifpb.gestao.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.gestao.domain.model.Campus;

@Repository
public interface CampusRepository extends JpaRepository<Campus, Long> {

}