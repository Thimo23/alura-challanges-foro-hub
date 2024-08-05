package foro.hub.api.domain.curso;

import jakarta.validation.constraints.NotBlank;

public record DTOCurso(
        @NotBlank
        String nombre,
        @NotBlank
        String categoria
) {
}
