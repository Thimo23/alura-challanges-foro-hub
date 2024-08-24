package foro.hub.api.domain.topico.validaciones;

import foro.hub.api.domain.topico.DTOActualizarTopico;
import foro.hub.api.domain.topico.DTORegistroTopico;
import foro.hub.api.domain.topico.TopicoRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicosNoDuplicados implements ValidadorDeDuplicados {
    @Autowired
    private TopicoRepository repository;

    @Override
    public void validar(DTORegistroTopico dtoRegistroTopico) {
        verificarTopicoDuplicado(dtoRegistroTopico.titulo(), dtoRegistroTopico.mensaje());
    }

    @Override
    public void validar(DTOActualizarTopico datos) {
        verificarTopicoDuplicado(datos.titulo(), datos.mensaje());
    }

    private void verificarTopicoDuplicado(String titulo,String mensaje){
        var topico = repository.findByTituloAndMensaje(titulo,mensaje);
        if(topico.isPresent()){
            throw new ValidationException("No se puede duplicar topicos ya existentes.");
        }
    }
}
