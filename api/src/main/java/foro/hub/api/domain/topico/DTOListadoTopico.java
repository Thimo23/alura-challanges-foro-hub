package foro.hub.api.domain.topico;

import foro.hub.api.domain.curso.Curso;
import foro.hub.api.domain.usuarios.DTOInfoUsuario;
import java.util.Date;

public record DTOListadoTopico(
        Long id,
        String titulo,
        Date fechaCreacion,
        TopicStatus status,
        DTOInfoUsuario autor,
        Curso curso
) {
    public DTOListadoTopico(Topico topico){
        this(topico.getId(),
                topico.getTitulo(),
                topico.getFechaCreacion(),
                topico.getStatus(),
                new DTOInfoUsuario(topico.getId(),topico.getAutor().getPerfil().getNombre()),
                topico.getCurso());
    }
}
