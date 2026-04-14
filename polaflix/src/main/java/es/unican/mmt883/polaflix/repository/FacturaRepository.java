package es.unican.mmt883.polaflix.repository;

import es.unican.mmt883.polaflix.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {
}
