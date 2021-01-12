package br.edu.ifpb.gestao.api.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import br.edu.ifpb.gestao.core.validation.FileContentType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DocumentoInputModel {
	
	@NotNull
	@FileContentType(allowed = {MediaType.APPLICATION_PDF_VALUE})
	private MultipartFile arquivo;
	
	@NotBlank
	private String nomeArquivo;
	
	private String contentType;
	
}