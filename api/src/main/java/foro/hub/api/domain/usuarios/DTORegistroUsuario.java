package foro.hub.api.domain.usuarios;

import foro.hub.api.domain.perfil.Perfil;
import jakarta.validation.constraints.*;

public record DTORegistroUsuario(
        @NotBlank
        @Size(min = 8,max = 14, message = "El usuario debe tener entre 8 y 12 caracteres")
        String login,
        @NotBlank
        @Size(min = 8, max = 14,message = "La contraseña debe tener entre 8 y 12 caracteres")
        @Pattern( regexp = "^(?=.*[A-Z])(?=.*\\d.*\\d.*\\d.*\\d).{8,}$",
                message = "La contraseña debe tener al menos una letra mayúscula, cuatro dígitos y al menos 8 caracteres en total.")
        String clave,
        @NotBlank
        @Email
        String email,
        @NotBlank //Poner un DTOPerfil para solo ingresar el nombre. La interfaz me agrega un campo ID que es auto generado, no se debe ingresar TODO
        @Size(min = 8,max = 14, message = "El nombre de perfil debe tener entre 8 y 14 caracteres.")
        String nombrePerfil
) {
}
