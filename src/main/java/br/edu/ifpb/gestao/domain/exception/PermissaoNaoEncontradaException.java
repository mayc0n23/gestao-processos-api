package br.edu.ifpb.gestao.domain.exception;

public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PermissaoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public PermissaoNaoEncontradaException(Long permissaoId) {
		this(String.format("Permissão de código '%d' não encontrada", permissaoId));
	}

}