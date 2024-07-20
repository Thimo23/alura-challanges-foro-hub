package foro.hub.api.domain.topico;

import foro.hub.api.domain.usuarios.Usuario;

import java.util.Date;

public record DTOListadoTopico(
        Long id,
        String titulo,
        String mensaje,
        Date fechaCreacion,
        TopicStatus status,
        Usuario autor,
        String curso
) {
    public DTOListadoTopico(Topico topico){
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion(),topico.getStatus(),topico.getAutor(),topico.getCurso());
    }
}
