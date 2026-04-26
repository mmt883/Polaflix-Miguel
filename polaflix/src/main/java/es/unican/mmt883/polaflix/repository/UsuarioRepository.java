package es.unican.mmt883.polaflix.repository;

import es.unican.mmt883.polaflix.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}



