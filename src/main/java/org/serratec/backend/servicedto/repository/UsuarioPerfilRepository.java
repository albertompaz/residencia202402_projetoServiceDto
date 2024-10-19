package org.serratec.backend.servicedto.repository;

import org.serratec.backend.servicedto.domain.UsuarioPerfil;
import org.serratec.backend.servicedto.domain.UsuarioPerfilPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioPerfilRepository extends JpaRepository<UsuarioPerfil, UsuarioPerfilPK> {

}
