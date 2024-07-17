package foro.hub.api.controller;

import foro.hub.api.domain.topico.DTORegistroTopico;
import foro.hub.api.domain.topico.DTOResponseTopic;
import foro.hub.api.domain.topico.Topico;
import foro.hub.api.domain.topico.TopicoRepository;
import foro.hub.api.domain.topico.validaciones.ValidadorDeTopicos;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private ValidadorDeTopicos validadorDeTopicos;

    @PostMapping
    public ResponseEntity<DTOResponseTopic> registrarTopico(@RequestBody @Valid DTORegistroTopico dtoRegistroTopico,
                                                            UriComponentsBuilder uriComponentsBuilder){
        validadorDeTopicos.validar(dtoRegistroTopico);
        Topico topico = topicoRepository.save(new Topico(dtoRegistroTopico));

        DTOResponseTopic dtoResponseTopic = new DTOResponseTopic(
                topico.getId(), topico.getTitulo(),topico.getMensaje(),
                topico.getFechaCreacion(),topico.getStatus(),topico.getAutor(),
                topico.getCurso());

        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(url).body(dtoResponseTopic);

    }
}
