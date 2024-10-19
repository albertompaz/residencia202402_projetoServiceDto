package org.serratec.backend.servicedto.service;

import java.util.Optional;

import org.serratec.backend.servicedto.domain.Endereco;
import org.serratec.backend.servicedto.dto.EnderecoDTO;
import org.serratec.backend.servicedto.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public EnderecoDTO buscar(String cep) {
		Optional<Endereco> enderecoOpt = Optional.ofNullable(enderecoRepository.findByCep(cep));
		
		if(enderecoOpt.isPresent()) {
			return new EnderecoDTO(enderecoOpt.get());
		} else {
			RestTemplate restTemplate = new RestTemplate();
			String url = "http://viacep.com.br/ws/" + cep + "/json/";
			Optional<Endereco> enderecoViaCepOpt = Optional.ofNullable
					(restTemplate.getForObject(url, Endereco.class));
			if (enderecoViaCepOpt.get().getCep() != null) {
				Endereco enderecoViaCep = enderecoViaCepOpt.get();
				String cepSemTraco = enderecoViaCep.getCep().replaceAll("-", "");
				enderecoViaCep.setCep(cepSemTraco);
				return inserir(enderecoViaCep);
			} else {
				return null;
			}
		}
	}
	
	private EnderecoDTO inserir(Endereco endereco) {
		endereco = enderecoRepository.save(endereco);
		EnderecoDTO enderecoDTO = new EnderecoDTO(endereco);
		return enderecoDTO;
	}
	
}
