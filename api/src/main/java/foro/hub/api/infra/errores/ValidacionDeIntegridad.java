package foro.hub.api.infra.errores;

public class ValidacionDeIntegridad extends RuntimeException{
    public ValidacionDeIntegridad(String message) {
        super(message);
    }
}
