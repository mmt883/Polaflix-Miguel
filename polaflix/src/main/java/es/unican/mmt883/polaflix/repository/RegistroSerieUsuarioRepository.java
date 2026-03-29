package es.unican.mmt883.polaflix.repository;

import es.unican.mmt883.polaflix.model.RegistroSerieUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistroSerieUsuarioRepository extends JpaRepository<RegistroSerieUsuario, Long> {
}
