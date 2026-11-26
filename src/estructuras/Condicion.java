package estructuras;

//INTERFAZ PROPIA PARA BUSQUEDAS CON CRITERIO (EJ: e -> e.getMatricula() == 50)
public interface Condicion<T> {
    boolean cumple(T elemento);
}
