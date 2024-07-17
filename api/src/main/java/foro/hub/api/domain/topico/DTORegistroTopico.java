package foro.hub.api.domain.topico;

import foro.hub.api.domain.usuarios.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record DTORegistroTopico(
       @NotBlank
        String titulo,
        @NotBlank
        String mensaje,
        @NotNull
        TopicStatus status,
        @NotNull
        Usuario autor,
        @NotBlank
        String curso) {
}
