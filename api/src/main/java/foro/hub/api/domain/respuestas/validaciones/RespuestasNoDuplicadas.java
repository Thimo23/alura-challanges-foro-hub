package foro.hub.api.domain.respuestas.validaciones;

import foro.hub.api.domain.respuestas.DTOActualizarRespuesta;
import foro.hub.api.domain.respuestas.DTORegistroRespuesta;
import foro.hub.api.domain.respuestas.RespuestaRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RespuestasNoDuplicadas implements ValidadorDeRespuestas{

    @Autowired
    RespuestaRepository respuestaRepository;
    @Override
    public void validar(DTORegistroRespuesta datos) {
        verificarRespuestaDuplicada(datos.mensaje());
    }

    @Override
    public void validar(DTOActualizarRespuesta datos) {
        verificarRespuestaDuplicada(datos.mensaje());
    }

    private void verificarRespuestaDuplicada(String mensaje){
        var respuesta = respuestaRepository.findByMensaje(mensaje);
        if(respuesta.isPresent()){
            throw new ValidationException("No se pueden duplicar respuestas.");
        }
    }
}
