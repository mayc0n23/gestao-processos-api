package br.edu.ifpb.gestao.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.gestao.domain.model.Documento;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {

}