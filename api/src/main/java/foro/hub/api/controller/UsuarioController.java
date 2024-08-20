package foro.hub.api.controller;

import foro.hub.api.domain.usuarios.DTORegistroUsuario;
import foro.hub.api.domain.usuarios.DTOResponseUsuario;
import foro.hub.api.domain.usuarios.UsuarioRepository;
import foro.hub.api.domain.usuarios.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Transactional
    @PostMapping
    public ResponseEntity<DTOResponseUsuario> registrarUsuario(@RequestBody @Valid DTORegistroUsuario dtoRegistroUsuario,
                                                               UriComponentsBuilder uriComponentsBuilder){
      DTOResponseUsuario nuevoUsuario = usuarioService.registrarUsuario(dtoRegistroUsuario);
      URI url = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(nuevoUsuario.id()).toUri();

    return ResponseEntity.created(url).body(nuevoUsuario);
    }
}
