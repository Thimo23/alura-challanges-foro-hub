package foro.hub.api.domain.topico.validaciones;

import foro.hub.api.domain.topico.DTOActualizarTopico;
import foro.hub.api.domain.topico.DTORegistroTopico;

public interface ValidadorDeTopicos {

    //Valida para registrar un topico
    public void validar(DTORegistroTopico datos);

    //Valida para actualizar un topico
    public void validar(DTOActualizarTopico datos);

}
