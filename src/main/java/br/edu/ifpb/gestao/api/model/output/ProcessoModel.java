package br.edu.ifpb.gestao.api.model.output;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProcessoModel {
	
	private Long id;
	
	private String nome;
	
	private List<GuiaModel> guias;
	
	private List<AnexoModel> anexos;
	
	private List<RegulamentoModel> regulamentos;
	
	private List<FAQModel> faqs;
	
}