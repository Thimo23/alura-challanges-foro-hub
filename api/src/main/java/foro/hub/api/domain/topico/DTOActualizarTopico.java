package foro.hub.api.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DTOActualizarTopico(
        @NotNull
        Long id,
        @NotBlank
        @Size(min = 3, max = 70, message = "El titulo debe tener en 3 y 70 caracteres")
        String titulo,
        @NotBlank
        @Size(min = 5, max = 1000, message = "El mensaje debe tener minimamente 5 caracteres.")
        String mensaje,
        @NotNull
        TopicStatus status
) {
}
