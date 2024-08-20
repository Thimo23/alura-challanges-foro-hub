package foro.hub.api.domain.respuestas.validaciones;

import foro.hub.api.domain.respuestas.DTOActualizarRespuesta;
import foro.hub.api.domain.respuestas.DTORegistroRespuesta;

public interface ValidadorDeRespuestas {

    public void validar(DTORegistroRespuesta dtoRegistroRespuesta);

    public void validar(DTOActualizarRespuesta dtoActualizarRespuesta);
}
