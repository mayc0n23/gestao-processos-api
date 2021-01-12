package br.edu.ifpb.gestao.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	
	ERRO_SISTEMA("Erro no sistema", "/erro-sistema"),
	
	DADOS_INVALIDOS("Dados inválidos", "/dados-invalidos"),
	
	PARAMETRO_INVALIDO("Parametro inválido", "/parametro-invalido"),
	
	MENSAGEM_INCOMPREENSIVEL("Mensagem incompreensivel", "/mensagem-incompreensivel"),
	
	ERRO_NEGOCIO("Viola regras de negócio", "/regra-negocio"),
	
	ENTIDADE_EM_USO("Entidade em uso", "/entidade-em-uso"),
	
	RECURSO_NAO_ENCONTRADO("Recurso não encontrado", "/recurso-nao-encontrado");
	
	private String title;
	private String uri;
	
	ProblemType(String title, String path) {
		this.title = title;
		this.uri = "https://localhost:8080" + path;
	}
	
}