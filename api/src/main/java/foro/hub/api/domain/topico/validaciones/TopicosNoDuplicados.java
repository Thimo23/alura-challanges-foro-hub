package foro.hub.api.domain.topico.validaciones;

import foro.hub.api.domain.topico.DTORegistroTopico;
import foro.hub.api.domain.topico.TopicoRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicosNoDuplicados implements ValidadorDeTopicos{
    @Autowired
    private TopicoRepository repository;

    @Override
    public void validar(DTORegistroTopico datos) {

        var topico = repository.findByTituloAndMensaje(datos.titulo(), datos.mensaje());

        if(topico.isPresent()){
            throw new ValidationException("No se puede duplicar topicos ya existentes.");
        }
    }
}
