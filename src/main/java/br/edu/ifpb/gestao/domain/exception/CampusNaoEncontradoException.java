package br.edu.ifpb.gestao.domain.exception;

public class CampusNaoEncontradoException extends EntidadeNaoEncontradaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CampusNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public CampusNaoEncontradoException(Long campusId) {
		this(String.format("Campus de código '%d' não foi encontrado", campusId));
	}

}