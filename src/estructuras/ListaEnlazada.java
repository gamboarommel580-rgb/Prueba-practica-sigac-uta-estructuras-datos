package estructuras;

import java.util.Iterator;
import java.util.NoSuchElementException;

//LISTA SIMPLEMENTE ENLAZADA CON PUNTERO A CABEZA Y A COLA
//agregar al inicio/final O(1), buscar/obtener/eliminar por criterio O(n)
public class ListaEnlazada<T> implements Iterable<T> {

    private static class Nodo<T> {
        T dato;
        Nodo<T> siguiente;

        Nodo(T dato) {
            this.dato = dato;
        }
    }

    private Nodo<T> cabeza;
    private Nodo<T> cola;
    private int tamanio;

    public void agregarAlInicio(T dato) {
        validarNulo(dato);
        Nodo<T> nuevo = new Nodo<T>(dato);
        nuevo.siguiente = cabeza;
        cabeza = nuevo;
        if (cola == null) {
            cola = nuevo;
        }
        tamanio++;
    }

    public void agregarAlFinal(T dato) {
        validarNulo(dato);
        Nodo<T> nuevo = new Nodo<T>(dato);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            cola.siguiente = nuevo;
        }
        cola = nuevo;
        tamanio++;
    }

    public void insertarEn(int posicion, T dato) {
        validarNulo(dato);
        if (posicion < 0 || posicion > tamanio) {
            throw new IndexOutOfBoundsException("Posicion fuera de rango: " + posicion);
        }
        if (posicion == 0) {
            agregarAlInicio(dato);
            return;
        }
        if (posicion == tamanio) {
            agregarAlFinal(dato);
            return;
        }
        Nodo<T> anterior = nodoEn(posicion - 1);
        Nodo<T> nuevo = new Nodo<T>(dato);
        nuevo.siguiente = anterior.siguiente;
        anterior.siguiente = nuevo;
        tamanio++;
    }

    public T obtener(int posicion) {
        validarPosicion(posicion);
        return nodoEn(posicion).dato;
    }

    public T primero() {
        if (cabeza == null) {
            throw new EstructuraVaciaException("La lista esta vacia");
        }
        return cabeza.dato;
    }

    public T ultimo() {
        if (cola == null) {
            throw new EstructuraVaciaException("La lista esta vacia");
        }
        return cola.dato;
    }

    public T eliminarEn(int posicion) {
        if (tamanio == 0) {
            throw new EstructuraVaciaException("La lista esta vacia");
        }
        validarPosicion(posicion);
        T eliminado;
        if (posicion == 0) {
            eliminado = cabeza.dato;
            cabeza = cabeza.siguiente;
            if (cabeza == null) {
                cola = null;
            }
        } else {
            Nodo<T> anterior = nodoEn(posicion - 1);
            Nodo<T> objetivo = anterior.siguiente;
            eliminado = objetivo.dato;
            anterior.siguiente = objetivo.siguiente;
            //si se borro el ultimo, la cola pasa a ser el anterior
            if (objetivo == cola) {
                cola = anterior;
            }
        }
        tamanio--;
        return eliminado;
    }

    //ELIMINA EL PRIMER ELEMENTO QUE CUMPLE LA CONDICION. Devuelve null si no hay ninguno
    public T eliminarPrimero(Condicion<T> condicion) {
        int indice = indiceDe(condicion);
        if (indice == -1) {
            return null;
        }
        return eliminarEn(indice);
    }

    public T buscar(Condicion<T> condicion) {
        validarCondicion(condicion);
        Nodo<T> actual = cabeza;
        while (actual != null) {
            if (condicion.cumple(actual.dato)) {
                return actual.dato;
            }
            actual = actual.siguiente;
        }
        return null;
    }

    public int indiceDe(Condicion<T> condicion) {
        validarCondicion(condicion);
        Nodo<T> actual = cabeza;
        int i = 0;
        while (actual != null) {
            if (condicion.cumple(actual.dato)) {
                return i;
            }
            actual = actual.siguiente;
            i++;
        }
        return -1;
    }

    public boolean contiene(Condicion<T> condicion) {
        return indiceDe(condicion) != -1;
    }

    public int tamanio() {
        return tamanio;
    }

    public boolean estaVacia() {
        return tamanio == 0;
    }

    public void limpiar() {
        cabeza = null;
        cola = null;
        tamanio = 0;
    }

    private Nodo<T> nodoEn(int posicion) {
        Nodo<T> actual = cabeza;
        for (int i = 0; i < posicion; i++) {
            actual = actual.siguiente;
        }
        return actual;
    }

    private void validarPosicion(int posicion) {
        if (posicion < 0 || posicion >= tamanio) {
            throw new IndexOutOfBoundsException("Posicion fuera de rango: " + posicion);
        }
    }

    private void validarNulo(T dato) {
        if (dato == null) {
            throw new IllegalArgumentException("No se permiten elementos null en la lista");
        }
    }

    private void validarCondicion(Condicion<T> condicion) {
        if (condicion == null) {
            throw new IllegalArgumentException("La condicion no puede ser null");
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Nodo<T> actual = cabeza;

            public boolean hasNext() {
                return actual != null;
            }

            public T next() {
                if (actual == null) {
                    throw new NoSuchElementException();
                }
                T dato = actual.dato;
                actual = actual.siguiente;
                return dato;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Nodo<T> actual = cabeza;
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
