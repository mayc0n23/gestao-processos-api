package br.edu.ifpb.gestao.domain.exception;

public class RegulamentoNaoEncontradoException extends EntidadeNaoEncontradaException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RegulamentoNaoEncontradoException(String mensagem) {
		super(mensagem);
		// TODO Auto-generated constructor stub
	}
	
	public RegulamentoNaoEncontradoException(Long regulamentoId) {
		this(String.format("Não existe um regulamento de código '%d' cadastrado nesse processo.", regulamentoId));
	}

}