package org.serratec.backend.servicedto.repository;

import org.serratec.backend.servicedto.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	// select * from Usuario u where u.email = email
	Usuario findByEmail(String email);

}
