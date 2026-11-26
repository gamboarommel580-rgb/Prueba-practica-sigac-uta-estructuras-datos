package estructuras;

import java.util.Iterator;
import java.util.NoSuchElementException;

//VECTOR DINAMICO IMPLEMENTADO A MANO SOBRE UN ARREGLO
//acceso por posicion O(1), insertar/eliminar en medio O(n), agregar al final O(1) amortizado
public class Vector<T> implements Iterable<T> {

    private Object[] datos;
    private int tamanio;

    public Vector() {
        this(4);
    }

    public Vector(int capacidadInicial) {
        if (capacidadInicial < 1) {
            throw new IllegalArgumentException("La capacidad inicial debe ser mayor a 0");
        }
        datos = new Object[capacidadInicial];
        tamanio = 0;
    }

    public void agregar(T elemento) {
        validarNulo(elemento);
        asegurarCapacidad();
        datos[tamanio] = elemento;
        tamanio++;
    }

    //PASO 1: validar posicion, PASO 2: correr a la derecha, PASO 3: colocar
    public void insertarEn(int posicion, T elemento) {
        validarNulo(elemento);
        if (posicion < 0 || posicion > tamanio) {
            throw new IndexOutOfBoundsException("Posicion fuera de rango: " + posicion);
        }
        asegurarCapacidad();
        for (int i = tamanio; i > posicion; i--) {
            datos[i] = datos[i - 1];
        }
        datos[posicion] = elemento;
        tamanio++;
    }

    @SuppressWarnings("unchecked")
    public T obtener(int posicion) {
        validarPosicion(posicion);
        return (T) datos[posicion];
    }

    @SuppressWarnings("unchecked")
    public T reemplazar(int posicion, T elemento) {
        validarNulo(elemento);
        validarPosicion(posicion);
        T anterior = (T) datos[posicion];
        datos[posicion] = elemento;
        return anterior;
    }

    //ELIMINACION SEGURA: se corre a la izquierda y se limpia la ultima celda
    @SuppressWarnings("unchecked")
    public T eliminarEn(int posicion) {
        if (tamanio == 0) {
            throw new EstructuraVaciaException("El vector esta vacio");
        }
        validarPosicion(posicion);
        T eliminado = (T) datos[posicion];
        for (int i = posicion; i < tamanio - 1; i++) {
            datos[i] = datos[i + 1];
        }
        datos[tamanio - 1] = null;
        tamanio--;
        return eliminado;
    }

    //BUSQUEDA SECUENCIAL O(n)
    @SuppressWarnings("unchecked")
    public int indiceDe(Condicion<T> condicion) {
        if (condicion == null) {
            throw new IllegalArgumentException("La condicion no puede ser null");
        }
        for (int i = 0; i < tamanio; i++) {
            if (condicion.cumple((T) datos[i])) {
                return i;
            }
        }
        return -1;
    }

    public int tamanio() {
        return tamanio;
    }

    public boolean estaVacio() {
        return tamanio == 0;
    }

    public int capacidad() {
        return datos.length;
    }

    //CRECE AL DOBLE CUANDO SE LLENA
    private void asegurarCapacidad() {
        if (tamanio == datos.length) {
            Object[] nuevo = new Object[datos.length * 2];
            for (int i = 0; i < tamanio; i++) {
                nuevo[i] = datos[i];
            }
            datos = nuevo;
        }
    }

    private void validarPosicion(int posicion) {
        if (posicion < 0 || posicion >= tamanio) {
            throw new IndexOutOfBoundsException("Posicion fuera de rango: " + posicion);
        }
    }

    private void validarNulo(T elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException("No se permiten elementos null en el vector");
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int actual = 0;

            public boolean hasNext() {
                return actual < tamanio;
            }

            @SuppressWarnings("unchecked")
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (T) datos[actual++];
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < tamanio; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(datos[i]);
        }
        return sb.append("]").toString();
    }
}
