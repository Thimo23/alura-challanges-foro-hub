package foro.hub.api.domain.topico;

import foro.hub.api.domain.respuestas.DTOResponseRespuesta;
import foro.hub.api.domain.respuestas.RespuestaRepository;
import foro.hub.api.domain.topico.validaciones.ValidadorDeDuplicados;
import foro.hub.api.domain.usuarios.DTOInfoUsuario;
import foro.hub.api.domain.usuarios.Usuario;
import foro.hub.api.infra.errores.AuthorizationException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class TopicoService {
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    List<ValidadorDeDuplicados> validadores;

    @Autowired
    RespuestaRepository respuestaRepository;


    public DTOResponseTopic actualizarTopico(DTOActualizarTopico datos){
        Usuario userDetailsAuthenticated = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var topico = topicoRepository.getReferenceById(datos.id());

        if(!userDetailsAuthenticated.getId().equals(topico.getAutor().getId())){
            throw new AuthorizationException("No tienes permisos para editar este topico");
        }

        validadores.forEach(v->v.validar(datos));

        topico.actualizarDatos(datos);
        topicoRepository.save(topico);

        return (new DTOResponseTopic(
                topico.getId(), topico.getTitulo(),topico.getMensaje(),
                topico.getFechaCreacion(),topico.getStatus(),
                new DTOInfoUsuario(topico.getId(), topico.getAutor().getPerfil().getNombre()),
                topico.getCurso(), topico.getNumRespuestas()));

    }

    public DTOResponseTopic registrarTopico(DTORegistroTopico datos){
        Usuario userDetailsAuthenticated = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Topico topico = new Topico(datos);

        topico.setAutor(userDetailsAuthenticated);

        validadores.forEach(v->v.validar(datos));
        topicoRepository.save(topico);

        return (new DTOResponseTopic(
                topico.getId(), topico.getTitulo(),topico.getMensaje(),
                topico.getFechaCreacion(),topico.getStatus(),
                new DTOInfoUsuario(topico.getId(), topico.getAutor().getPerfil().getNombre()),
                topico.getCurso(), topico.getNumRespuestas()));

    }

    public void eliminarTopico(Long id){

        Usuario userDetailsAuthenticated = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        var topico = topicoRepository.findById(id);

        if(!topico.isPresent()){
            throw new EntityNotFoundException("El tópico con ID " + id + " no existe");
        }

        if(!userDetailsAuthenticated.getId().equals(topico.get().getAutor().getId())){
            throw new AuthorizationException("No estas autorizado a eliminar este topico");
        }

        topicoRepository.deleteById(id);

    }

    public DTOTopicoYRespuestas retonarDatosTopico(Long id, Pageable pag){
        Topico topico = topicoRepository.getReferenceById(id);

        DTOResponseTopic dtoResponseTopic = new DTOResponseTopic(
                topico.getId(), topico.getTitulo(),topico.getMensaje(),
                topico.getFechaCreacion(),topico.getStatus(),
                new DTOInfoUsuario(topico.getId(),topico.getAutor().getPerfil().getNombre()),
                topico.getCurso(), topico.getNumRespuestas());

        Page<DTOResponseRespuesta> dtoResponseRespuestas = respuestaRepository.findAllByTopicoIdOrderBySolucionDesc(id,pag)
                .map(DTOResponseRespuesta::new);


        return new DTOTopicoYRespuestas(dtoResponseTopic,dtoResponseRespuestas);
    }

}
