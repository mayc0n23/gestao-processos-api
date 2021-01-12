package br.edu.ifpb.gestao.domain.exception;

public class FaqNaoEncontradoException extends EntidadeNaoEncontradaException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FaqNaoEncontradoException(String mensagem) {
		super(mensagem);
		// TODO Auto-generated constructor stub
	}
	
	public FaqNaoEncontradoException(Long faqId) {
		this(String.format("Não existe uma pergunta de código '%d' cadastrado nesse processo.", faqId));
	}

}