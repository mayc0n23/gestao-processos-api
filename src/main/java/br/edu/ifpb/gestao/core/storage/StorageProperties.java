package br.edu.ifpb.gestao.core.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.amazonaws.regions.Regions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component
@ConfigurationProperties("processmanager.storage")
public class StorageProperties {
	
	private S3 s3 = new S3();
	
	@Setter
	@Getter
	public class S3 {
		
		private String idChaveAcesso;
		private String chaveAcessoSecreta;
		private String bucket;
		//alterar para Regions
		private Regions regiao;
		private String diretorioFotos;
		
	}
	
}