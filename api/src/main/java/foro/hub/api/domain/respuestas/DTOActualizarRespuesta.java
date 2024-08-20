package foro.hub.api.domain.respuestas;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DTOActualizarRespuesta(
        @NotNull
        Long iDRespuesta,
        @NotBlank
        @Size(min = 5,max = 455,message = "El mensaje debe tener al menos 5 caracteres.")
        String mensaje
) {
}
