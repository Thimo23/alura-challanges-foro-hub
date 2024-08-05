package foro.hub.api.domain.topico;

import foro.hub.api.domain.curso.DTOCurso;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DTORegistroTopico(
       @NotBlank
        String titulo,
        @NotBlank
        String mensaje,
        @NotNull
        TopicStatus status,
        @NotNull
        @Valid
        DTOCurso curso) {
}
