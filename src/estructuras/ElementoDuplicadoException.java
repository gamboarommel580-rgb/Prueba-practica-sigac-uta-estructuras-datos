package estructuras;

//SE LANZA CUANDO SE INTENTA REGISTRAR UN IDENTIFICADOR QUE YA EXISTE
public class ElementoDuplicadoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ElementoDuplicadoException(String mensaje) {
        super(mensaje);
    }
}
