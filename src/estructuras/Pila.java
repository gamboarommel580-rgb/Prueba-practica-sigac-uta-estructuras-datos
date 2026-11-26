package estructuras;

//PILA LIFO CON NODOS ENLAZADOS. apilar, desapilar y cima son O(1)
public class Pila<T> {

    private static class Nodo<T> {
        T dato;
        Nodo<T> debajo;

        Nodo(T dato, Nodo<T> debajo) {
            this.dato = dato;
            this.debajo = debajo;
        }
    }

    private Nodo<T> tope;
    private int tamanio;

    public void apilar(T dato) {
        if (dato == null) {
            throw new IllegalArgumentException("No se puede apilar null");
        }
        tope = new Nodo<T>(dato, tope);
        tamanio++;
    }

    public T desapilar() {
        if (tope == null) {
            throw new EstructuraVaciaException("La pila esta vacia");
        }
        T dato = tope.dato;
        tope = tope.debajo;
        tamanio--;
        return dato;
    }

    public T cima() {
        if (tope == null) {
            throw new EstructuraVaciaException("La pila esta vacia");
        }
        return tope.dato;
    }

    public boolean estaVacia() {
        return tope == null;
    }

    public int tamanio() {
        return tamanio;
    }

    //SE MUESTRA DESDE LA CIMA HACIA LA BASE
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Nodo<T> actual = tope;
        while (actual != null) {
            sb.append(actual.dato);
            if (actual.debajo != null) {
                sb.append(", ");
            }
            actual = actual.debajo;
        }
        return sb.append("]").toString();
    }
}
