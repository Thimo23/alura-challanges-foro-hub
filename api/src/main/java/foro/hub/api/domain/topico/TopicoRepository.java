package foro.hub.api.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico, Long> {


    Optional<Topico> findByTituloAndMensaje(String titulo,String mensaje);

    @Override
    Optional<Topico> findById(Long id);

    Page<Topico>findAllByOrderByFechaCreacionDesc(Pageable paginacion);
}
