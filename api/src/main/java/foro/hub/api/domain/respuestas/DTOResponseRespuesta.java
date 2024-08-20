package foro.hub.api.domain.respuestas;

import java.util.Date;

public record DTOResponseRespuesta(
        Long id,
        String mensaje,
        Long topicoID,
        Date fechaCreacion,
        Long autorRespuestaID,
        boolean solucion
) {
    public DTOResponseRespuesta(Respuesta respuesta){
        this(respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getTopico().getId(),
                respuesta.getFechaCreacion(),
                respuesta.getAutor().getId(),
                respuesta.isSolucion());
    }
}
