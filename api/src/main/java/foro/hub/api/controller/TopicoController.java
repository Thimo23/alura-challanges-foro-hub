package foro.hub.api.controller;

import foro.hub.api.domain.topico.*;
import foro.hub.api.domain.topico.validaciones.ValidadorDeTopicos;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.Optional;

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
        validadorDeTopicos.validar(dtoRegistroTopico.titulo(), dtoRegistroTopico.mensaje());
        Topico topico = topicoRepository.save(new Topico(dtoRegistroTopico));

        DTOResponseTopic dtoResponseTopic = new DTOResponseTopic(
                topico.getId(), topico.getTitulo(),topico.getMensaje(),
                topico.getFechaCreacion(),topico.getStatus(),topico.getAutor(),
                topico.getCurso());

        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(url).body(dtoResponseTopic);

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
                topico.getFechaCreacion(),topico.getStatus(),topico.getAutor(),
                topico.getCurso());

        return ResponseEntity.ok(dtoResponseTopic);
    }

    @PutMapping
    public ResponseEntity actualizarTopico(@RequestBody @Valid DTOActualizarTopico dtoActualizarTopico){
        validadorDeTopicos.validar(dtoActualizarTopico.titulo(), dtoActualizarTopico.mensaje());
        Optional<Topico> optionalTopico = topicoRepository.findById(dtoActualizarTopico.id());

        if(optionalTopico.isPresent()){
            Topico topico = optionalTopico.get();
            topico.actualizarDatos(dtoActualizarTopico);
            topicoRepository.save(topico);

            return ResponseEntity.ok(new DTOResponseTopic(
                    topico.getId(), topico.getTitulo(),topico.getMensaje(),
                    topico.getFechaCreacion(),topico.getStatus(),topico.getAutor(),
                    topico.getCurso())

            );
        }
        else{
            throw new ValidationException("No existe el topico buscado");
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity eliminarTopico(@PathVariable @Valid Long id){

        Optional<Topico> optionalTopico = topicoRepository.findById(id);

        if(optionalTopico.isPresent()){
            Topico topico = optionalTopico.get();
            topicoRepository.deleteById(topico.getId());

            return ResponseEntity.ok(new DTOResponseTopic(
                    topico.getId(), topico.getTitulo(),topico.getMensaje(),
                    topico.getFechaCreacion(),topico.getStatus(),topico.getAutor(),
                    topico.getCurso()));
        }
        else{
            throw new ValidationException("El topico buscado no existe");
        }
    }

}
