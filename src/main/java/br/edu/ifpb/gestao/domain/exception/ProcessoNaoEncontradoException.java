package br.edu.ifpb.gestao.domain.exception;

public class ProcessoNaoEncontradoException extends EntidadeNaoEncontradaException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public ProcessoNaoEncontradoException(String mensagem) {
		super(mensagem);
		// TODO Auto-generated constructor stub
	}
	
	public ProcessoNaoEncontradoException(Long campusId, Long processoId) {
		this(String.format("Não existe um processo de código '%d' cadastrado no Campus de código '%d'", processoId, campusId));
	}
}