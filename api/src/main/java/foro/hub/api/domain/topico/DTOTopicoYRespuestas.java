package foro.hub.api.domain.topico;

import foro.hub.api.domain.respuestas.DTOResponseRespuesta;
import org.springframework.data.domain.Page;

import java.util.List;

public record DTOTopicoYRespuestas(
        DTOResponseTopic topico,
        Page<DTOResponseRespuesta> respuestas
) {
}
