package foro.hub.api.controller;

import foro.hub.api.domain.topico.*;

import foro.hub.api.domain.usuarios.DTOInfoUsuario;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private TopicoService topicoService;

    @PostMapping
    public ResponseEntity<DTOResponseTopic> registrarTopico(@RequestBody @Valid DTORegistroTopico dtoRegistroTopico,
                                                            UriComponentsBuilder uriComponentsBuilder){

        DTOResponseTopic topicoRegistrado = topicoService.registrarTopico(dtoRegistroTopico);
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topicoRegistrado.id()).toUri();

        return ResponseEntity.created(url).body(topicoRegistrado);

    }

    @GetMapping
    public ResponseEntity<Page<DTOListadoTopico>> listadoTopicos(@PageableDefault(size = 2) Pageable paginacion) {
        return ResponseEntity.ok(topicoRepository.findAllByOrderByFechaCreacionDesc(paginacion).map(DTOListadoTopico::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DTOResponseTopic>retornarDatosTopico(@PathVariable @Valid Long id){
        Topico topico = topicoRepository.getReferenceById(id);
        DTOResponseTopic dtoResponseTopic = new DTOResponseTopic(
                topico.getId(), topico.getTitulo(),topico.getMensaje(),
                topico.getFechaCreacion(),topico.getStatus(),
                new DTOInfoUsuario(topico.getId(),topico.getAutor().getPerfil().getNombre()),
                topico.getCurso());

        return ResponseEntity.ok(dtoResponseTopic);
    }

    @PutMapping
    public ResponseEntity actualizarTopico(@RequestBody @Valid DTOActualizarTopico dtoActualizarTopico){
        DTOResponseTopic topicoActualizado = topicoService.actualizarTopico(dtoActualizarTopico);
        return ResponseEntity.ok(topicoActualizado);

    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity eliminarTopico(@PathVariable @Valid Long id){
        topicoService.eliminarTopico(id);
        return ResponseEntity.noContent().build();
    }

}
