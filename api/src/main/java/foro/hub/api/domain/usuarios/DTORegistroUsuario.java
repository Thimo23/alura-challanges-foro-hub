package foro.hub.api.domain.usuarios;

import foro.hub.api.domain.perfil.Perfil;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DTORegistroUsuario(
        @NotBlank
        String login,
        @NotBlank
        String clave,
        @NotBlank
        @Email
        String email,
        @NotNull
        Perfil perfil
) {
}
