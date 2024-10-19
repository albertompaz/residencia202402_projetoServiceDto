package org.serratec.backend.servicedto.repository;

import org.serratec.backend.servicedto.domain.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

	Endereco findByCep(String cep);
	
}
