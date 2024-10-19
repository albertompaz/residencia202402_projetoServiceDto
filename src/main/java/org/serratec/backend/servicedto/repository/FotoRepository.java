package org.serratec.backend.servicedto.repository;

import java.util.Optional;

import org.serratec.backend.servicedto.domain.Foto;
import org.serratec.backend.servicedto.domain.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FotoRepository extends JpaRepository<Foto, Long> {

	Optional<Foto> findByFuncionario(Funcionario funcionario);
	
}
