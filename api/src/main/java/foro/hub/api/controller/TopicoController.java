package foro.hub.api.controller;


import foro.hub.api.domain.respuestas.RespuestaRepository;
import foro.hub.api.domain.topico.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;


@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private TopicoService topicoService;

    @Autowired
    RespuestaRepository respuestaRepository;

    @Transactional
    @PostMapping
    @Operation(summary = "Registra un nuevo tópico en la base de datos.")
    public ResponseEntity<DTOResponseTopic> registrarTopico(@RequestBody @Valid DTORegistroTopico dtoRegistroTopico,
                                                            UriComponentsBuilder uriComponentsBuilder){

        DTOResponseTopic topicoRegistrado = topicoService.registrarTopico(dtoRegistroTopico);
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topicoRegistrado.id()).toUri();

        return ResponseEntity.created(url).body(topicoRegistrado);

    }


    @GetMapping
    @Operation(summary = "Lista todos los tópicos registrados en la base de datos.")
    public ResponseEntity<Page<DTOListadoTopico>> listadoTopicos(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "2") int size,
            @RequestParam(value = "sort", defaultValue = "fechaCreacion") String sort) {

        Pageable paginacion = PageRequest.of(page, size, Sort.by(Sort.Order.by(sort)));
        return ResponseEntity.ok(topicoRepository.findAllByOrderByFechaCreacionDesc(paginacion).map(DTOListadoTopico::new));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Devuelve un tópico específico junto con todas sus respuestas.")
    public ResponseEntity<DTOTopicoYRespuestas>retornarDatosTopico(
            @PathVariable @Valid Long id,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "2") int size,
            @RequestParam(value = "sort", defaultValue = "solucion") String sort){

       Pageable pag = PageRequest.of(page, size, Sort.by(Sort.Order.by(sort)));
       DTOTopicoYRespuestas dtoTopicoYRespuestas = topicoService.retonarDatosTopico(id,pag);
       return ResponseEntity.ok(dtoTopicoYRespuestas);
    }

    @Transactional
    @PutMapping
    @Operation(summary = "Actualiza un tópico específico en la base de datos.")
    public ResponseEntity actualizarTopico(@RequestBody @Valid DTOActualizarTopico dtoActualizarTopico){
        DTOResponseTopic topicoActualizado = topicoService.actualizarTopico(dtoActualizarTopico);
        return ResponseEntity.ok(topicoActualizado);

    }

    @Transactional
    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un topico en la base de datos.")
    public ResponseEntity eliminarTopico(@PathVariable @Valid Long id){
        topicoService.eliminarTopico(id);
        return ResponseEntity.noContent().build();
    }

}
