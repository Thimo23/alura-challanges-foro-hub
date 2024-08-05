package foro.hub.api.domain.usuarios;

import foro.hub.api.domain.perfil.Perfil;

public record DTOResponseUsuario(
        Long id,
        String login,
        String clave,
        String email,
        Perfil perfil) {

    public DTOResponseUsuario(Usuario usuario){
        this(usuario.getId(), usuario.getLogin(), usuario.getClave(), usuario.getEmail(), usuario.getPerfil());
    }
}
