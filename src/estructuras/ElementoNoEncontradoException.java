package estructuras;

//SE LANZA CUANDO SE PIDE UN IDENTIFICADOR QUE NO EXISTE
public class ElementoNoEncontradoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ElementoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
