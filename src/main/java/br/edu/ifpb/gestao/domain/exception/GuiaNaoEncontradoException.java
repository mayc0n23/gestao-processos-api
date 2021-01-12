package br.edu.ifpb.gestao.domain.exception;

public class GuiaNaoEncontradoException extends EntidadeNaoEncontradaException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public GuiaNaoEncontradoException(String mensagem) {
		super(mensagem);
		// TODO Auto-generated constructor stub
	}
	
	public GuiaNaoEncontradoException(Long guiaId) {
		this(String.format("Não existe um guia de código '%d' cadastro nesse processo.", guiaId));
	}

}