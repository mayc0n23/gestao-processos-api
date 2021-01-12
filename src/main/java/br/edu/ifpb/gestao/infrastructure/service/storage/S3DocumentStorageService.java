package br.edu.ifpb.gestao.infrastructure.service.storage;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import br.edu.ifpb.gestao.core.storage.StorageProperties;
import br.edu.ifpb.gestao.domain.service.DocumentStorageService;

@Service
public class S3DocumentStorageService implements DocumentStorageService {
	
	@Autowired
	private StorageProperties storageProperties;
	
	@Autowired
	private AmazonS3 amazonS3;

	@Override
	public RecoveredDocument recuperar(String nomeArquivo) {
		String caminhoArquivo = getCaminhoArquivo(nomeArquivo);
		
		URL url = amazonS3.getUrl(storageProperties.getS3().getBucket(), caminhoArquivo);
		return RecoveredDocument.builder()
				.url(url.toString())
				.build();
	}

	@Override
	public void armazenar(NewDocument newDocument) {
		try {
			String caminhoArquivo = getCaminhoArquivo(newDocument.getNomeArquivo());
			
			var objectMetadata = new ObjectMetadata();
			objectMetadata.setContentType(newDocument.getContentType());
			
			var putObjectRequest = new PutObjectRequest(
					storageProperties.getS3().getBucket(),
					caminhoArquivo,
					newDocument.getInputStream(),
					objectMetadata
				).withCannedAcl(CannedAccessControlList.PublicRead);
			
			amazonS3.putObject(putObjectRequest);
		} catch (Exception e) {
			throw new StorageException("Não foi possivel enviar o arquivo para Amazon S3", e);
		}
	}

	@Override
	public void remover(String nomeArquivo) {
		try {
			String caminhoArquivo = getCaminhoArquivo(nomeArquivo);
			var deleteObjectRequest = new DeleteObjectRequest(storageProperties.getS3().getBucket(), caminhoArquivo);
			amazonS3.deleteObject(deleteObjectRequest);
		} catch (Exception e) {
			throw new StorageException("Não foi possivel remover o arquivo da Amazon S3", e);
		}
	}
	
	private String getCaminhoArquivo(String nomeArquivo) {
		return String.format("%s/%s", storageProperties.getS3().getDiretorioFotos(), nomeArquivo);
	}

}