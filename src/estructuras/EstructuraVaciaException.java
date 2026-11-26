package estructuras;

//SE LANZA CUANDO SE INTENTA SACAR O CONSULTAR DE UNA ESTRUCTURA SIN ELEMENTOS
public class EstructuraVaciaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EstructuraVaciaException(String mensaje) {
        super(mensaje);
    }
}
