package br.edu.ifpb.gestao.domain.service;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface DocumentStorageService {
	
	RecoveredDocument recuperar(String nomeArquivo);
	
	void armazenar(NewDocument newDocument);
	
	void remover(String nomeArquivo);
	
	default String gerarNomeArquivo(String nomeOriginal) {
		return UUID.randomUUID() + "_" + nomeOriginal;
	}
	
	@Builder
	@Getter
	class NewDocument {
		private String nomeArquivo;
		private String contentType;
		private InputStream inputStream;
	}
	
	@Builder
	@Getter
	class RecoveredDocument {
		
		private InputStream inputStream;
		private String url;
		
		public boolean temUrl() {
			return url != null;
		}
		
		public boolean temInputStream() {
			return inputStream != null;
		}
		
	}
	
}