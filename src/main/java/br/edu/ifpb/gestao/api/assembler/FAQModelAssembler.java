package br.edu.ifpb.gestao.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifpb.gestao.api.model.output.FAQModel;
import br.edu.ifpb.gestao.domain.model.FAQ;

@Component
public class FAQModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public FAQModel toModel(FAQ faq) {
		return modelMapper.map(faq, FAQModel.class);
	}
	
	public List<FAQModel> toCollectionModel(List<FAQ> faqs) {
		return faqs.stream()
				.map(faq -> toModel(faq))
				.collect(Collectors.toList());
	}
	
}