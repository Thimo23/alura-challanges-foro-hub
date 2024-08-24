package foro.hub.api.domain.topico;

import foro.hub.api.domain.curso.Curso;
import foro.hub.api.domain.usuarios.DTOInfoUsuario;
import java.util.Date;

public record DTOResponseTopic(Long id,
                               String titulo,
                               String mensaje,
                               Date fechaCreacion,
                               TopicStatus status,
                               DTOInfoUsuario autor,
                               Curso curso,
                               int cantRespuestas) {

    public DTOResponseTopic(Topico topico) {
        this(topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getStatus(),
                new DTOInfoUsuario(topico.getAutor().getId(),topico.getAutor().getPerfil().getNombre()),
                topico.getCurso(),
                topico.getNumRespuestas());
    }
}

