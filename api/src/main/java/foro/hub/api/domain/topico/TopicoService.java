package foro.hub.api.domain.topico;

import foro.hub.api.domain.topico.validaciones.ValidadorDeTopicos;
import foro.hub.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicoService {
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    List<ValidadorDeTopicos> validadores;

    public DTOResponseTopic actualizarTopico(DTOActualizarTopico datos){
        var topico = topicoRepository.getReferenceById(datos.id());
        validadores.forEach(v->v.validar(datos));

        topico.actualizarDatos(datos);
        topicoRepository.save(topico);

        return (new DTOResponseTopic(
                topico.getId(), topico.getTitulo(),topico.getMensaje(),
                topico.getFechaCreacion(),topico.getStatus(),topico.getAutor(),
                topico.getCurso()));

    }

    public DTOResponseTopic registrarTopico(DTORegistroTopico datos){
        var topico = new Topico(datos);
        validadores.forEach(v->v.validar(datos));
        topicoRepository.save(topico);

        return (new DTOResponseTopic(
                topico.getId(), topico.getTitulo(),topico.getMensaje(),
                topico.getFechaCreacion(),topico.getStatus(),topico.getAutor(),
                topico.getCurso()));

    }

}
