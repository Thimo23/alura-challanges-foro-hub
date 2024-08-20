package foro.hub.api.domain.usuarios;

import foro.hub.api.domain.perfil.Perfil;

public record DTOResponseUsuario(
        Long id,
        String login,
        String email,
        Perfil perfil) {

    public DTOResponseUsuario(Usuario usuario){
        this(usuario.getId(), usuario.getLogin(), usuario.getEmail(), usuario.getPerfil());
    }
}
