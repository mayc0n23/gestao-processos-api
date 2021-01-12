package br.edu.ifpb.gestao.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifpb.gestao.api.model.input.FAQInputModel;
import br.edu.ifpb.gestao.domain.model.FAQ;

@Component
public class FAQInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public FAQ toDomainObject(FAQInputModel faqInput) {
		return modelMapper.map(faqInput, FAQ.class);
	}
	
}