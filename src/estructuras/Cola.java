package estructuras;

//COLA FIFO CON NODOS ENLAZADOS. encolar y desencolar son O(1)
public class Cola<T> {

    private static class Nodo<T> {
        T dato;
        Nodo<T> siguiente;

        Nodo(T dato) {
            this.dato = dato;
        }
    }

    private Nodo<T> frente;
    private Nodo<T> fin;
    private int tamanio;

    public void encolar(T dato) {
        if (dato == null) {
            throw new IllegalArgumentException("No se puede encolar null");
        }
        Nodo<T> nuevo = new Nodo<T>(dato);
        if (fin == null) {
            frente = nuevo;
        } else {
            fin.siguiente = nuevo;
        }
        fin = nuevo;
        tamanio++;
    }

    public T desencolar() {
        if (frente == null) {
            throw new EstructuraVaciaException("La cola esta vacia");
        }
        T dato = frente.dato;
        frente = frente.siguiente;
        if (frente == null) {
            fin = null;
        }
        tamanio--;
        return dato;
    }

    public T frente() {
        if (frente == null) {
            throw new EstructuraVaciaException("La cola esta vacia");
        }
        return frente.dato;
    }

    public boolean contiene(Condicion<T> condicion) {
        if (condicion == null) {
            throw new IllegalArgumentException("La condicion no puede ser null");
        }
        Nodo<T> actual = frente;
        while (actual != null) {
            if (condicion.cumple(actual.dato)) {
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    public boolean estaVacia() {
        return frente == null;
    }

    public int tamanio() {
        return tamanio;
    }

    //SE MUESTRA DEL FRENTE AL FINAL
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Nodo<T> actual = frente;
        while (actual != null) {
            sb.append(actual.dato);
            if (actual.siguiente != null) {
                sb.append(", ");
            }
            actual = actual.siguiente;
        }
        return sb.append("]").toString();
    }
}
