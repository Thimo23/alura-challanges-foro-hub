package foro.hub.api.domain.respuestas;


import foro.hub.api.domain.respuestas.validaciones.ValidadorDeRespuestas;
import foro.hub.api.domain.topico.TopicoRepository;
import foro.hub.api.domain.usuarios.Usuario;
import foro.hub.api.domain.usuarios.UsuarioRepository;
import foro.hub.api.infra.errores.AuthorizationException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RespuestaService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    List<ValidadorDeRespuestas> validadores;

    public DTOResponseRespuesta registrarRespuesta(DTORegistroRespuesta dtoRegistroRespuesta){

        Usuario userDetailsAuthenticated = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Respuesta nuevaRespuesta = new Respuesta(dtoRegistroRespuesta.mensaje());

        nuevaRespuesta.setAutor(userDetailsAuthenticated);

        var topicoApuntado = topicoRepository.findById(dtoRegistroRespuesta.topicoID());

        if(topicoApuntado.isEmpty()){
            throw new EntityNotFoundException();
        }
        //Si el topico apuntado no existe la aplicación devolvera "404 entity not found". Este error se produce a nivel base de datos pero lo manejamos de esta menera.

        validadores.forEach(v -> v.validar(dtoRegistroRespuesta));
        nuevaRespuesta.setTopico(topicoApuntado.get());

        respuestaRepository.save(nuevaRespuesta);
        topicoRepository.incrementarNumeroDeRespuestas(nuevaRespuesta.getTopico().getId());

        return (new DTOResponseRespuesta(
                nuevaRespuesta.getId(),
                nuevaRespuesta.getMensaje(),
                nuevaRespuesta.getTopico().getId(),
                nuevaRespuesta.getFechaCreacion(),
                nuevaRespuesta.getAutor().getId(),
                nuevaRespuesta.isSolucion()
        ));
    }

    public DTOResponseRespuesta actualizarRespuesta(DTOActualizarRespuesta dtoActualizarRespuesta){
        Usuario userDetailsAuthenticated = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var respuesta = respuestaRepository.getReferenceById(dtoActualizarRespuesta.iDRespuesta());

        if(!userDetailsAuthenticated.getId().equals(respuesta.getAutor().getId())){
            throw new AuthorizationException("No tienes permisos para editar esta respuesta");
        }

        validadores.forEach(v -> v.validar(dtoActualizarRespuesta));
        respuesta.actualizarDatos(dtoActualizarRespuesta);
        respuestaRepository.save(respuesta);

        return (new DTOResponseRespuesta(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getTopico().getId(),
                respuesta.getFechaCreacion(),
                respuesta.getAutor().getId(),
                respuesta.isSolucion()
        ));
    }
}
