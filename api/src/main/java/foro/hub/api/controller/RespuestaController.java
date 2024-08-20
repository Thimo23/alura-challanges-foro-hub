package foro.hub.api.controller;

import foro.hub.api.domain.respuestas.DTOActualizarRespuesta;
import foro.hub.api.domain.respuestas.DTOResponseRespuesta;
import foro.hub.api.domain.respuestas.RespuestaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import foro.hub.api.domain.respuestas.DTORegistroRespuesta;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/respuestas")
public class RespuestaController {
    @Autowired
    private RespuestaService respuestaService;

    @Transactional
    @PostMapping
    ResponseEntity registrarRespuesta(@RequestBody @Valid DTORegistroRespuesta dtoRegistroRespuesta,
                                      UriComponentsBuilder uriComponentsBuilder){
        DTOResponseRespuesta nuevaRespuesta = respuestaService.registrarRespuesta(dtoRegistroRespuesta);
        URI url = uriComponentsBuilder.path("/respuestas/{id}").buildAndExpand(nuevaRespuesta.id()).toUri();

        return ResponseEntity.created(url).body(nuevaRespuesta);

    }

    @Transactional
    @PutMapping
    ResponseEntity actualizarRespuesta(@RequestBody @Valid DTOActualizarRespuesta dtoActualizarRespuesta){
        DTOResponseRespuesta dtoResponseRespuesta = respuestaService.actualizarRespuesta(dtoActualizarRespuesta);
        return ResponseEntity.ok(dtoResponseRespuesta);
    }
}
