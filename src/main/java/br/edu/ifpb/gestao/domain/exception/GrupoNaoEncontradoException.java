package br.edu.ifpb.gestao.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GrupoNaoEncontradoException(String mensagem) {
		super(mensagem);
		// TODO Auto-generated constructor stub
	}
	
	public GrupoNaoEncontradoException(Long grupoId) {
		this(String.format("Grupo de código '%d' não encontrado", grupoId));
	}

}