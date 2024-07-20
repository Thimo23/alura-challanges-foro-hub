package foro.hub.api.domain.topico.validaciones;

import foro.hub.api.domain.topico.TopicoRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicosNoDuplicados implements ValidadorDeTopicos{
    @Autowired
    private TopicoRepository repository;

    @Override
    public void validar(String titulo,String mensaje) {

        var topico = repository.findByTituloAndMensaje(titulo,mensaje);

        if(topico.isPresent()){
            throw new ValidationException("No se puede duplicar topicos ya existentes.");
        }
    }
}
