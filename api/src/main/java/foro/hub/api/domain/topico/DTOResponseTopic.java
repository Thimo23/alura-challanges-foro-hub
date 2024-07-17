package foro.hub.api.domain.topico;

import foro.hub.api.domain.usuarios.Usuario;
import jakarta.persistence.*;

import java.util.Date;

public record DTOResponseTopic(Long id,
                               String titulo,
                               String mensaje,
                               Date fechaCreacion,
                               TopicStatus status,
                               Usuario autor,
                               String curso) {

    public DTOResponseTopic(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion(),topico.getStatus(),topico.getAutor(),topico.getCurso());
    }
}

