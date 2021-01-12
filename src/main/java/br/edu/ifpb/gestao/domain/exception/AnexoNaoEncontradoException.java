package br.edu.ifpb.gestao.domain.exception;

public class AnexoNaoEncontradoException extends EntidadeNaoEncontradaException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AnexoNaoEncontradoException(String mensagem) {
		super(mensagem);
		// TODO Auto-generated constructor stub
	}
	
	public AnexoNaoEncontradoException(Long anexoId) {
		this(String.format("Não existe um anexo de código '%d' cadastrado nesse processo.", anexoId));
	}

}